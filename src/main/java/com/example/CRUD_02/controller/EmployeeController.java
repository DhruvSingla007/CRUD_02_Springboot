package com.example.CRUD_02.controller;

import com.example.CRUD_02.model.AuthRequest;
import com.example.CRUD_02.model.AuthResponse;
import com.example.CRUD_02.model.Employee;
import com.example.CRUD_02.model.ResponseDTO;
import com.example.CRUD_02.service.AuthenticateService;
import com.example.CRUD_02.service.EmployeeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthenticateService authenticateService;

    @GetMapping("/")
    public String welcome(){
        return "welcome";
    }


    @PostMapping(value = "/api/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @NonNull AuthRequest authRequestDTO) {
        System.out.println("Reached");
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = authenticateService.registerUser(authRequestDTO);
        } catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @NonNull AuthRequest authRequestDTO) {
        System.out.println("Reached");
        AuthResponse authResponseDTO = null;
        try {
            authResponseDTO = authenticateService.authenticateUser(authRequestDTO);
        } catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
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
