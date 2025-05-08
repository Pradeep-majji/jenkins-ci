package com.example.employeemanagement.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
 
import org.postgresql.util.PGobject;
 
import com.example.employeemanagement.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
    
@Service
public class EmpService {
 
    @Autowired
    private DataSource dataSource;
 
    public Employee getEmployeeById(int empId) throws SQLException {
        Employee employee = new Employee();
 
        Connection conn = dataSource.getConnection();
        CallableStatement cs = conn.prepareCall("CALL getEmployeeById(?, ?)");
 
        cs.setInt(1, empId);
        // Register OUT parameter as Types.OTHER (since we're returning a JSON)
        cs.registerOutParameter(2, Types.OTHER);
 
        cs.execute();
 
        // Retrieve the OUT parameter (JSON object)
        Object outParam = cs.getObject(2);
 
        if (outParam instanceof PGobject) {
            PGobject pgObject = (PGobject) outParam;
            String jsonValue = pgObject.getValue(); // This will be a JSON string
 
            // Now, map the JSON string to the Employee object
            // You can use a JSON parsing library like Jackson or Gson to deserialize it
            // For simplicity, let's assume a basic example here:
 
            // Example: Parsing the JSON manually (using Jackson or any other JSON library)
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(jsonValue);
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 
            employee.setEmpid(jsonNode.get("empid").asInt());
            employee.setEmpname(jsonNode.get("empname").asText());
            employee.setSalary(jsonNode.get("salary").asInt());
            employee.setDeptno(jsonNode.get("deptno").asInt());
        }
 
        cs.close();
        conn.close();
 
        return employee;
    }
}