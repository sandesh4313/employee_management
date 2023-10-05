package com.acer.employeemanagementsystem.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/login", "/sign-up", "/search", "/error/**").permitAll()
                .antMatchers("/css/**", "/js/**", "/Images/Products/**", "/products").permitAll()
                .antMatchers("/updateEmployee/**", "/deleteEmployee/**", "/saveEmployee").hasAuthority("ADMIN") // Only allow admin for update, delete, and save
                .antMatchers("/").hasAnyAuthority("ADMIN", "TEST_USER") // Allow admin and test user for view homepage
                .antMatchers("/showNewEmployeeForm").hasAuthority("ADMIN") // Only allow admin for showing new employee form
                .antMatchers("/showFormForUpdate/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()
                .and()
//                .formLogin()
//                    .permitAll()
//                    .defaultSuccessUrl("/")
                .formLogin().permitAll()
                .defaultSuccessUrl("/", false)
                .successHandler((request, response, authentication) -> {
                    // Handle success authentication and redirect based on role
                    String targetUrl = determineTargetUrl(authentication);
                    response.sendRedirect(targetUrl);
                })
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null && authentication.getAuthorities().stream()
                            .anyMatch(authority -> authority.getAuthority().equals("TEST_USER"))) {
                        response.sendRedirect("/not-admin");
                    } else {
                        response.sendRedirect("/403");
                    }
                });
    }

    public String determineTargetUrl(Authentication authentication) {
        // Get the user's authorities/roles
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check if the user has admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        // Determine the target URL based on the user's role
        if (isAdmin) {
            return "/admin-site";
        } else {
            return "/";
        }
    }
}
