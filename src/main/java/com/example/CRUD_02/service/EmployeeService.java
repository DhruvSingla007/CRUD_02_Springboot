package com.example.CRUD_02.service;

import com.example.CRUD_02.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAllEmployees();
    Employee createEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    void deleteEmployee(Long id);

    Employee updateEmployeeById(Long id, Employee employee);
}
