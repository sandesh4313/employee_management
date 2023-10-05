package com.acer.employeemanagementsystem.config;


import com.acer.employeemanagementsystem.enitity.Role;
import com.acer.employeemanagementsystem.enitity.User;
import com.acer.employeemanagementsystem.repository.RoleRepo;
import com.acer.employeemanagementsystem.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class InitDbConfig {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @PostConstruct
    public void doEntries(){
        if(roleRepo.findAll().size() == 0){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role savedAdmin = roleRepo.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            Role savedUser = roleRepo.save(userRole);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User newAdmin  = new User();
            newAdmin.setFirst_name("Ram");
            newAdmin.setLast_name("Charan");
            newAdmin.setUsername("admin");
            newAdmin.setEmail("admin@gmail.com");
            newAdmin.setPassword(encoder.encode("admin"));
            newAdmin.setRoles(Arrays.asList(savedAdmin));
            userRepo.save(newAdmin);

            User nonAdminUser = new User();
            nonAdminUser.setFirst_name("test");
            nonAdminUser.setUsername("test");
            nonAdminUser.setPassword(encoder.encode("test"));
            nonAdminUser.setRoles(Arrays.asList(savedUser));
            userRepo.save(nonAdminUser);


        }
    }

}
