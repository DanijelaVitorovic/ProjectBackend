package com.dex.coreserver.controller;

import com.dex.coreserver.model.OrganizationalUnit;
import com.dex.coreserver.model.UniqueCodeValidator;
import com.dex.coreserver.repository.OrganizationalUnitRepository;
import com.dex.coreserver.service.MapValidationErrorService;
import com.dex.coreserver.service.OrganizationalUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/organizationalUnit")
public class OrganizationalUnitController {

    @Autowired
    OrganizationalUnitRepository organizationalUnitRepository;

    @Autowired
    OrganizationalUnitService organizationalUnitService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @Autowired
    UniqueCodeValidator uniqueCodeValidator;

    @PostMapping("/create")
    public ResponseEntity<?> createNewOrganizationalUnit(@Valid @RequestBody OrganizationalUnit organizationalUnit, BindingResult result, Principal principal) throws Exception {

        if(organizationalUnitRepository.existsByCode(organizationalUnit.getCode())) {
            throw new Exception("Organizaciona jedinica sa ovim kodom vec postoji");
        }

        OrganizationalUnit createdOrganizationalUnit = organizationalUnitService.create(organizationalUnit,principal.getName());

        return new ResponseEntity<OrganizationalUnit>(createdOrganizationalUnit, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOrganizationalUnit(@Valid @RequestBody OrganizationalUnit organizationalUnit, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap != null)
            return errorMap;

        OrganizationalUnit updatedOrganizationalUnit = organizationalUnitService.update(organizationalUnit, principal.getName());
        return new ResponseEntity<OrganizationalUnit>(updatedOrganizationalUnit, HttpStatus.CREATED);
    }

    @GetMapping("/find/{unit_id}")
    public ResponseEntity<?> findOrganizationalUnitById(@PathVariable Long unit_id) {
        OrganizationalUnit organizationalUnit = organizationalUnitService.findById(unit_id);
        return new ResponseEntity<OrganizationalUnit>(organizationalUnit, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Principal principal) {
        List<OrganizationalUnit> organizationalUnits = organizationalUnitService.findAll(principal.getName());
        return new ResponseEntity<List<OrganizationalUnit>>(organizationalUnits, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{unit_id}")
    public ResponseEntity<?> delete(@PathVariable Long unit_id, Principal principal) {
        organizationalUnitService.delete(unit_id, principal.getName());
        return new ResponseEntity<String>("Organizational Unit deleted", HttpStatus.OK);
    }

}
