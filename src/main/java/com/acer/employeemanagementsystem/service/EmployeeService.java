package com.acer.employeemanagementsystem.service;

import com.acer.employeemanagementsystem.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployee();
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Integer id);

    EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto);

    Integer deleteEmployeeById(Integer id);
}
