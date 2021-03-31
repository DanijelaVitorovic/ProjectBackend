package com.dex.coreserver.service;

import com.dex.coreserver.model.AbstractDataModel;
import com.dex.coreserver.model.Employee;

import java.util.List;

public interface BasicService<T extends AbstractDataModel>{
    T create(final T entity, final String username);
    T update(final T entity, final String username);
    List<T> delete(final Long id, final String username);
    List<T> findAll(String username);
    T findById(final Long id);
}
