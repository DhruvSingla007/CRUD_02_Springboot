package com.example.CRUD_02.controller;

import com.example.CRUD_02.model.Employee;
import com.example.CRUD_02.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
public class EmployeeController {

    EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/api/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @PostMapping(value = "/api/createEmployee", consumes = "application/json")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/api/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping(value = "/api/updateEmployeeById/{id}")
    public Employee updateEmployeeById(@PathVariable("id") Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployeeById(id, employee);
    }

    @DeleteMapping("/api/deleteEmployee/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(true);
    }


}
