package com.acer.employeemanagementsystem.repository;

import com.acer.employeemanagementsystem.enitity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}
