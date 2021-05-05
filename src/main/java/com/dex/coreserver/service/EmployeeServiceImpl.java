package com.dex.coreserver.service;

import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.PhysicalEntity;
import com.dex.coreserver.model.User;
import com.dex.coreserver.repository.EmployeeRepository;
import com.dex.coreserver.repository.PhysicalEntityRepository;
import com.dex.coreserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Employee create(Employee employee, String username) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee, String username) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id, String username) {}

    @Override
    public List<Employee> findAll(String username) {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public List<Employee> deleteByIdAndReturnFindAll(Long id, String username) {
        employeeRepository.deleteById(id);
        return employeeRepository.findAll();
    }

    @Override
    public List<User> FindAllUsersNotUsedAsForeignKeyInTableEmployee(){
        List<User> lista = userRepository.findAllNotUsedAsForeignKeyInTableEmployee();
        return lista;
    }

    @Override
    public Employee findByUserId(Long userId) {
        return employeeRepository.findByUserId(userId);
    }

    @Override
    public Employee findEmployeeByUser(User user){
        return findByUserId(user.getId());
    }
}
