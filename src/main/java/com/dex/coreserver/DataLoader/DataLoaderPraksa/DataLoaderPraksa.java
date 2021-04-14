package com.dex.coreserver.DataLoader.DataLoaderPraksa;

import com.dex.coreserver.model.*;
import com.dex.coreserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoaderPraksa implements CommandLineRunner {

    @Autowired
    private CaseClassificationRepository caseClassificationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PhysicalEntityRepository physicalEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        //PEs

        PhysicalEntity newPhysicalEntity1 = new PhysicalEntity();
        newPhysicalEntity1.setFirstName("Sanja");
        newPhysicalEntity1.setLastName("Djukanovic");
        newPhysicalEntity1.setMiddleName("S");
        Address address1 = new Address();
        address1.setCity("Beograd");
        newPhysicalEntity1.setAddress(address1);
        physicalEntityRepository.save(newPhysicalEntity1);

        PhysicalEntity newPhysicalEntity2 = new PhysicalEntity();
        newPhysicalEntity2.setFirstName("Marko");
        newPhysicalEntity2.setLastName("Markovic");
        newPhysicalEntity2.setMiddleName("M");
        Address address2 = new Address();
        address2.setCity("Smederevo");
        newPhysicalEntity2.setAddress(address2);
        physicalEntityRepository.save(newPhysicalEntity2);

        PhysicalEntity newPhysicalEntity3 = new PhysicalEntity();
        newPhysicalEntity3.setFirstName("Danijela");
        newPhysicalEntity3.setLastName("Danicic");
        newPhysicalEntity3.setMiddleName("D");
        Address address3 = new Address();
        address3.setCity("Novi Sad");
        newPhysicalEntity3.setAddress(address3);
        physicalEntityRepository.save(newPhysicalEntity3);

        //Roles

        Role newRole = new Role();
        newRole.setRoleName("ADMIN");
        roleRepository.save(newRole);

        //Users

        User newUser1 = new User();
        newUser1.setFirstName("Sanja");
        newUser1.setLastName("Djuaknovic");
        newUser1.setUsername("sanja.dj17@gmail.com");
        newUser1.setPassword("ssssss");
        List<Role> roles= new ArrayList<>();
        roles.add(newRole);
        newUser1.setRoles(roles);
       userRepository.save(newUser1);

        User newUser2 = new User();
        newUser2.setFirstName("Marko");
        newUser2.setLastName("Stepanovic");
        newUser2.setUsername("m@gmail.com");
        newUser2.setPassword("$2a$10$TW0KurGnMjhuBjjPu1g48OuiuigrHlxxJkiw8d3MhWIpTDYPHG81G");
        userRepository.save(newUser2);

        //Employees

        Employee newEmployee1 = new Employee();
        newEmployee1.setManager(true);
        newEmployee1.setProfession("Profesija 1");
        newEmployee1.setUser(newUser1);
        newEmployee1.setPhysicalEntity(newPhysicalEntity1);
        employeeRepository.save(newEmployee1);

        Employee newEmployee2 = new Employee();
        newEmployee2.setManager(true);
        newEmployee2.setProfession("Profesija 2");
        newEmployee2.setUser(newUser2);
        newEmployee2.setPhysicalEntity(newPhysicalEntity2);
        employeeRepository.save(newEmployee2);

        Employee newEmployee3 = new Employee();
        newEmployee3.setManager(true);
        newEmployee3.setProfession("Profesija 3");
        newEmployee3.setUser(newUser2);
        newEmployee3.setPhysicalEntity(newPhysicalEntity3);
        employeeRepository.save(newEmployee3);


    }

}
