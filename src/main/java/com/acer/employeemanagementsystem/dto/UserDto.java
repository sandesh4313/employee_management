package com.acer.employeemanagementsystem.dto;

import com.acer.employeemanagementsystem.enitity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String first_name;

    private String last_name;

    private String username;

    private String email;

    private String password;

    private String address;

    private Integer phone;

    public UserDto(User savedUser) {
        this.id=getId();
        this.first_name=getLast_name();

        this.email=getEmail();
        this.password=getPassword();
        this.address=getAddress();
        this.phone=getPhone();

    }
}
