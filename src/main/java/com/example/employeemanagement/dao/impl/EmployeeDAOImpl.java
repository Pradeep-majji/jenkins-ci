package com.example.employeemanagement.dao.impl;

import com.example.employeemanagement.dao.EmployeeDAO;
import com.example.employeemanagement.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Employee> getEmployeeByIdProcedure(int empId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("getEmployeeById")
                .declareParameters(
                        new SqlParameter("p_empid", Types.INTEGER),
                        new SqlOutParameter("p_emp", Types.STRUCT, "emp_record_type")
                )
                .withoutProcedureColumnMetaDataAccess();

        Map<String, Object> result = jdbcCall.execute(Map.of("p_empid", empId));
        
        if (result != null && result.get("p_emp") != null) {
            Map<String, Object> empData = (Map<String, Object>) result.get("p_emp");
            Employee emp = new Employee();
            emp.setEmpid(((Number) empData.get("empid")).intValue());
            emp.setEmpname((String) empData.get("empname"));
            emp.setSalary(((Number) empData.get("salary")).intValue());
            emp.setDeptno(((Number) empData.get("deptno")).intValue());
            return Optional.of(emp);
        }
        
        return Optional.empty();
    }
} 