package com.company.complaintapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(users.username("rayyan").password("test123").roles("USER"))
                .withUser(users.username("abdulrub").password("test123").roles("USER"))
                .withUser(users.username("affan").password("test123").roles("USER"))
                .withUser(users.username("manager").password("test123").roles("MANAGER"));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/complaints/list").hasAnyRole("USER", "MANAGER")
                .antMatchers("/complaints/showAddForm").hasRole("USER")
                .antMatchers("/complaints/showUpdateForm").hasRole("MANAGER")
                .antMatchers("/complaints/showUpdateForm*").hasRole("MANAGER")
                .antMatchers(("/complaints/saveComplaint")).hasAnyRole("USER", "MANAGER")
                .antMatchers("/complaints/delete").hasRole("USER")
//                .antMatchers("/complaint/**").permitAll()
                .antMatchers("/").hasAnyRole("USER", "MANAGER")
                .and()
                .formLogin()
                    .loginPage("/showLoginPage")
                    .loginProcessingUrl("/authenticateTheUser")
                    .permitAll()
                    .and()
                        .logout().permitAll()
                        .and()
                        .exceptionHandling().accessDeniedPage("/access-denied");
    }

}
