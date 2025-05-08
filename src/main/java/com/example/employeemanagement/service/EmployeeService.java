package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    Employee getEmployeeByIdUsingProcedure(int id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
} 