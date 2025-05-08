package com.example.employeemanagement.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empid;
    
    @NotBlank(message = "Employee name is required")
    @Column(name = "empname")
    private String empname;
    
    @NotNull(message = "Salary is required")
    @Column(name = "salary")
    private Integer salary;
    
    @NotNull(message = "Department number is required")
    @Column(name = "deptno")
    private Integer deptno;


    public Employee() {
    }

    public Employee(Integer empid, String empname, Integer salary, Integer deptno) {
        this.empid = empid;
        this.empname = empname;
        this.salary = salary;
        this.deptno = deptno;
    }


    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }


} 