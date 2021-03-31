package com.dex.coreserver.service;

import com.dex.coreserver.model.Employee;
import com.dex.coreserver.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee, String username) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee, String username) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> delete(Long id, String username) {
        employeeRepository.deleteById(id);
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findAll(String username) {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }
}
