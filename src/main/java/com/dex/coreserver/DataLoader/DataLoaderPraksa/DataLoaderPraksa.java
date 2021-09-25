package com.dex.coreserver.DataLoader.DataLoaderPraksa;

import com.dex.coreserver.model.*;
import com.dex.coreserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoaderPraksa implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PhysicalEntityRepository physicalEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findAll().size() == 0) {

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

            Role newRole = new Role();
            newRole.setRoleName("ADMIN");
            roleRepository.save(newRole);

            User newUser1 = new User();

            newUser1.setUsername("sanja.dj17@gmail.com");
            newUser1.setPassword(bCryptPasswordEncoder.encode("ssssss"));
            newUser1.setActive("DA");
            List<Role> rolesForUser1 = new ArrayList<>();
            rolesForUser1.add(newRole);
            newUser1.setRoles(rolesForUser1);
            userRepository.save(newUser1);

            User newUser2 = new User();
            newUser2.setUsername("m@gmail.com");
            newUser2.setPassword(bCryptPasswordEncoder.encode("ssssss"));
            newUser2.setActive("DA");
            List<Role> rolesForUser2 = new ArrayList<>();
            rolesForUser2.add(newRole);
            newUser1.setRoles(rolesForUser2);
            userRepository.save(newUser2);

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

        }
    }
}
