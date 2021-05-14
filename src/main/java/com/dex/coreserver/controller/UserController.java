package com.dex.coreserver.controller;

import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.User;
import com.dex.coreserver.payload.JWTLoginSuccessResponse;
import com.dex.coreserver.payload.LoginRequest;
import com.dex.coreserver.security.JwtTokenProvider;
import com.dex.coreserver.service.MapValidationErrorService;
import com.dex.coreserver.service.UserServiceImpl;
import com.dex.coreserver.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;

import static com.dex.coreserver.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTLoginSuccessResponse(true,jwt));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody User user, BindingResult result, Principal principal) {
        userValidator.validate(user,result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;
        User user1 = userServiceImpl.createUser(user, principal.getName());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public List<User> findAll(Principal principal) {
        return userServiceImpl.findAll(principal.getName());
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findUserById(@PathVariable Long id, Principal principal) {
        User user = userServiceImpl.findUserById(id,principal.getName());
        user.setConfirmPassword(user.getPassword());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/findAllDeleted")
    public List<User> findAllDeleted(Principal principal) {
        return userServiceImpl.findAllDeleted(principal.getName());
    }

    @PatchMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> deactivateUser(@PathVariable Long id, Principal principal){
        return userServiceImpl.deactivateUser(id,principal.getName());
    }

    @PatchMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> activateUser(@PathVariable Long id, Principal principal){
        return userServiceImpl.activateUser(id,principal.getName());
    }

    @PatchMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> deleteUser(@PathVariable Long id, Principal principal){
        return userServiceImpl.deleteUser(id,principal.getName());
    }

    @GetMapping("/findUserByUsername")
    public Employee findUserByUsername(Principal principal) {
        return userServiceImpl.findEmployeeByUserUsername(principal.getName());
    }
}
