package com.dex.coreserver.service;

import com.dex.coreserver.model.Employee;

import java.util.List;

public interface EmployeeService extends BasicService<Employee>{
    public List<Employee> deleteByIdAndReturnFindAll(Long id, String username);
}
