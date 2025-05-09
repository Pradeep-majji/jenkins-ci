package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    @Procedure(procedureName = "getEmployeeById", outputParameterName = "emp")
    Employee getEmployeeByIdProcedure(@Param("p_empid") int empId);
} 