package com.dex.coreserver.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dex.coreserver.repository.OrganizationalUnitRepository;
import com.dex.coreserver.service.OrganizationalUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueCodeValidator implements ConstraintValidator<UniqueCode, String>{

    @Autowired
    OrganizationalUnitRepository organizationalUnitRepository;

    @Autowired
    private OrganizationalUnitService organizationalUnitService;

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return code != null && !organizationalUnitRepository.existsByCode(code);
    }
}
