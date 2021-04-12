package com.dex.coreserver.service;

import com.dex.coreserver.model.OrganizationalUnit;
import com.dex.coreserver.repository.OrganizationalUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

    @Autowired
    OrganizationalUnitRepository organizationalUnitRepository;

    @Override
    public OrganizationalUnit create(OrganizationalUnit entity, String username) {
        return organizationalUnitRepository.save(entity);
    }

    @Override
    public OrganizationalUnit update(OrganizationalUnit entity, String username) {
        return organizationalUnitRepository.save(entity);
    }

    @Override
    public void delete(Long id, String username) {
        organizationalUnitRepository.deleteById(id);
    }

    @Override
    public List<OrganizationalUnit> findAll(String username) {
        return organizationalUnitRepository.findAll();
    }

    @Override
    public OrganizationalUnit findById(Long id) {
        return organizationalUnitRepository.findById(id).get();
    }
}
