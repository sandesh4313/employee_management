package com.acer.employeemanagementsystem.controller;

import com.acer.employeemanagementsystem.dto.EmployeeDto;
import com.acer.employeemanagementsystem.enitity.Employee;
import com.acer.employeemanagementsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // display list of employees
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployee());
        return "index";
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") EmployeeDto employeeDto) {
        // save employee to database
        employeeService.saveEmployee(employeeDto);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {

        // get employee from the service
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employeeDto);
        return "update_employee";
    }
    @PostMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable(value = "id") Integer id,
                                 @ModelAttribute("employee") @Validated EmployeeDto employeeDto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update_employee";
        }

        employeeService.updateEmployee(id, employeeDto);
        return "redirect:/";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Integer id) {

        // call delete employee method
        employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }

}
