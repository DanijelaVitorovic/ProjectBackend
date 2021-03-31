package com.dex.coreserver.controller;

import com.dex.coreserver.model.Employee;
import com.dex.coreserver.service.EmployeeService;
import com.dex.coreserver.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeContoller {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Employee employee, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        Employee newEmployee = employeeService.create(employee, principal.getName());
        return new ResponseEntity<Employee>(newEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody Employee employee, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        Employee updatedEmployee =  employeeService.update(employee, principal.getName());
        return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public List<Employee> findAll(Principal principal) {
        return employeeService.findAll(principal.getName());
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findEmployeeById(@PathVariable Long id, Principal principal) {
        Employee employee = employeeService.findById(id);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> deleteEmployer(@PathVariable Long id, Principal principal){
        return employeeService.delete(id,principal.getName());
    }
}
