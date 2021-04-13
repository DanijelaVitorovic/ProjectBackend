package com.dex.coreserver.service;

import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.PhysicalEntity;
import com.dex.coreserver.model.User;

import java.util.List;

public interface EmployeeService extends BasicService<Employee>{
    public List<Employee> deleteByIdAndReturnFindAll(Long id, String username);
    public List<User> FindAllUsersNotUsedAsForeignKeyInTableEmployee();

}
