package com.acer.employeemanagementsystem.service.impl;

import com.acer.employeemanagementsystem.dto.EmployeeDto;
import com.acer.employeemanagementsystem.enitity.Employee;
import com.acer.employeemanagementsystem.repository.EmployeeRepository;
import com.acer.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<EmployeeDto> returnList = new ArrayList<>();
        List<Employee> allData = employeeRepository.findAll();
        for (Employee each : allData) {
            returnList.add(new EmployeeDto(each));
        }
        return returnList;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        // Convert EmployeeDto to Employee entity
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        // Save the employee entity
        Employee savedEmployee = employeeRepository.save(employee);

        // Convert saved Employee entity to EmployeeDto
        return new EmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        Employee employee = employeeRepository.getById(id);
        return new EmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.getById(id);
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDto(savedEmployee);
    }

    @Override
    @Transactional
    public Integer deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
        return id;
    }
}
