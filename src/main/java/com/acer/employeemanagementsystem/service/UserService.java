package com.acer.employeemanagementsystem.service;


import com.acer.employeemanagementsystem.dto.UserDto;

public interface UserService {

    UserDto saveUserToTable(UserDto userDto);
}
