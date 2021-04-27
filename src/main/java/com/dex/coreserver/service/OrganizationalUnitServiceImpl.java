package com.dex.coreserver.service;

import com.dex.coreserver.model.LegalEntity;
import com.dex.coreserver.model.OrganizationalUnit;
import com.dex.coreserver.repository.LegalEntityRepository;
import com.dex.coreserver.repository.OrganizationalUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

    @Autowired
    OrganizationalUnitRepository organizationalUnitRepository;

    @Autowired
    LegalEntityRepository legalEntityRepository;

    @Override
    public OrganizationalUnit create(OrganizationalUnit entity, String username) {
        LegalEntity legalEntity = legalEntityRepository.findById(entity.getLegalEntity().getId()).get();
        entity.setLegalEntity(legalEntity);
        return organizationalUnitRepository.save(entity);
    }

    @Override
    public OrganizationalUnit update(OrganizationalUnit entity, String username) {
        LegalEntity legalEntity = legalEntityRepository.findById(entity.getLegalEntity().getId()).get();
        entity.setLegalEntity(legalEntity);
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

//    @Override
//    @Transactional
//    public boolean isCodeAlreadyInUse(String code){
//        boolean organizationalUnitInDB = true;
//        if (organizationalUnitRepository.getActiveOrganizationalUnit(code) != null) organizationalUnitInDB = false;
//        return organizationalUnitInDB;
//    }
}
