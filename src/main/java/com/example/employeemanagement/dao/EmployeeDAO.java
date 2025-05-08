package com.example.employeemanagement.dao;

import com.example.employeemanagement.model.Employee;
import java.util.Optional;

public interface EmployeeDAO {
    Optional<Employee> getEmployeeByIdProcedure(int empId);
} 