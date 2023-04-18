package com.example.CRUD_02.service;

import com.example.CRUD_02.model.Employee;
import com.example.CRUD_02.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    EmployeeRepository repository;

    EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.repository = employeeRepository;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("EMPLOYEE NOT FOUND"));
    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Employee updateEmployeeById(Long id, Employee employee) {
        Employee currEmployee = repository.findById(id).orElse(null);
        if(currEmployee != null){
            repository.save(employee);
        }
        return employee;
    }
}
