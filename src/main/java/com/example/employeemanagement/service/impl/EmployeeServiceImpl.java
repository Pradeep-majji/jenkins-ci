package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dao.EmployeeDAO;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.EmpService;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmpService empService;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee getEmployeeByIdUsingProcedure(int id) {
        Employee employee = null;
        try {
            employee = empService.getEmployeeById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);
        if (employeeDetails.getEmpname() != null) {
            employee.setEmpname(employeeDetails.getEmpname());
        }
        if (employeeDetails.getSalary() != null) {
            employee.setSalary(employeeDetails.getSalary());
        }
        if (employeeDetails.getDeptno() != null) {
            employee.setDeptno(employeeDetails.getDeptno());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
} 