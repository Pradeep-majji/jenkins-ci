package com.example.employeemanagement.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import oracle.sql.STRUCT;
import oracle.jdbc.OracleTypes;
 
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
        CallableStatement cs = conn.prepareCall("{ call getEmployeeById(?, ?) }");
 
        // Set the input parameter
        cs.setInt(1, empId);
        
        // Register OUT parameter as STRUCT (for emp_record_type)
        cs.registerOutParameter(2, OracleTypes.STRUCT, "EMP_RECORD_TYPE");
 
        cs.execute();
 
        // Get the STRUCT object
        STRUCT struct = (STRUCT) cs.getObject(2);
        if (struct != null) {
            Object[] attributes = struct.getAttributes();
            employee.setEmpid(((Number) attributes[0]).intValue());
            employee.setEmpname((String) attributes[1]);
            employee.setSalary(((Number) attributes[2]).intValue());
            employee.setDeptno(((Number) attributes[3]).intValue());
        }
 
        cs.close();
        conn.close();
 
        return employee;
    }
}