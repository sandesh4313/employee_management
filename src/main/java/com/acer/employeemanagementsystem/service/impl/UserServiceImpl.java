package com.acer.employeemanagementsystem.service.impl;


import com.acer.employeemanagementsystem.dto.UserDto;
import com.acer.employeemanagementsystem.enitity.User;
import com.acer.employeemanagementsystem.repository.RoleRepo;
import com.acer.employeemanagementsystem.repository.UserRepo;
import com.acer.employeemanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;


    @Override
    public UserDto saveUserToTable(UserDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User newUser = new User();

        newUser.setFirst_name(dto.getFirst_name());
        newUser.setLast_name(dto.getLast_name());
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(encoder.encode(dto.getPassword()));
        newUser.setEmail(dto.getEmail());
        newUser.setPhone(dto.getPhone());
        newUser.setAddress(dto.getAddress());

        newUser.setRoles(roleRepo.getUserRole("USER"));

        User savedUser = userRepo.save(newUser);

        return new UserDto(savedUser);
    }

}
