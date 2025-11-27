package com.ems.controller;

import com.ems.dto.*;
import com.ems.entity.Employee;
import com.ems.entity.Login;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.LoginRepository;
import com.ems.service.UserDetailsImpl;
import com.ems.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final LoginRepository loginRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("sign...");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        System.out.println(authentication+"ak");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getEmployeeId().longValue(),
                userDetails.getEmail(),
                userDetails.getEmail(),
                roles
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        System.out.println("singup");
        if (loginRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        // Create Employee
        Employee employee = new Employee();
        employee.setFirstName(signUpRequest.getFirstName());
        employee.setLastName(signUpRequest.getLastName());
        employee.setEmail(signUpRequest.getEmail());
        employee.setRole(signUpRequest.getRoles());
        employee.setStatus(Employee.EmployeeStatus.ACTIVE);

        Employee savedEmployee = employeeRepository.save(employee);

        // Create Login entry
        Login login = new Login();
        login.setEmail(signUpRequest.getEmail());
        login.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        login.setEmployeeId(savedEmployee.getId());
        loginRepository.save(login);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
