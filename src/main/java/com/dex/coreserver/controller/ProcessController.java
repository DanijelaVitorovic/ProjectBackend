package com.dex.coreserver.controller;

import com.dex.coreserver.model.Process;
import com.dex.coreserver.service.MapValidationErrorService;
import com.dex.coreserver.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.PortUnreachableException;
import java.security.Principal;
import java.util.List;
import java.util.function.LongFunction;

@RestController
@RequestMapping("/api/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewProcess(@Valid @RequestBody Process process, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap != null) {
            return errorMap;
        }

        Process createProcess = processService.create(process, principal.getName());
        return new ResponseEntity<Process>(createProcess, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?>  updateProcess(@Valid @RequestBody Process process, BindingResult result,  Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap != null) {
            return errorMap;
        }

        Process updateProcess = processService.update(process, principal.getName());
        return new ResponseEntity<Process>(updateProcess, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findProcessById(@PathVariable Long id) {
        Process process = processService.findById(id);
        return new ResponseEntity<Process>(process, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Principal principal)  {
        List<Process> processes = processService.findAll(principal.getName());
        return new ResponseEntity<List<Process>>(processes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<String>("Process deleted", HttpStatus.OK);
    }
}
