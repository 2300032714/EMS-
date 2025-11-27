package com.ems.service;

import com.ems.entity.Login;
import com.ems.entity.Employee;
import com.ems.repository.LoginRepository;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginRepository loginRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        Login login = loginRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        System.out.println(login+"aks");
        Employee employee = employeeRepository.findById(login.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("Employee Not Found for login: " + email));
        System.out.println(employee);
        return UserDetailsImpl.build(login, employee);
    }
}