package com.acer.employeemanagementsystem.security;



import com.acer.employeemanagementsystem.enitity.User;
import com.acer.employeemanagementsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepo.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username Not found.");
        }
        return user;
    }
}
