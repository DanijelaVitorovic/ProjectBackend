package com.dex.coreserver.controller;

import com.dex.coreserver.model.ProcessType;
import com.dex.coreserver.service.MapValidationErrorService;
import com.dex.coreserver.service.ProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/processType")
public class ProcessTypeController {

    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewType(@Valid  @RequestBody ProcessType processType, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if (errorMap != null)
            return errorMap;

        ProcessType createdProcessType = processTypeService.create(processType, principal.getName());
        return new ResponseEntity<ProcessType>(createdProcessType, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProcessType(@Valid @RequestBody ProcessType processType, BindingResult result, Principal principal) {
        ResponseEntity<?>  errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap  != null)
            return errorMap;

        ProcessType updatedProcessType = processTypeService.update(processType, principal.getName());
        return new ResponseEntity<ProcessType>(updatedProcessType, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findProcessTypeById(@PathVariable Long id)  {
        ProcessType processType = processTypeService.findById(id);
        return new ResponseEntity<ProcessType>(processType, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Principal principal)  {
        List<ProcessType> processTypes = processTypeService.findAll(principal.getName());
        return new ResponseEntity<List<ProcessType>>(processTypes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        processTypeService.delete(id, principal.getName());
        return new ResponseEntity<String>("ProcessType deletegit mgit d", HttpStatus.OK);
    }
}
