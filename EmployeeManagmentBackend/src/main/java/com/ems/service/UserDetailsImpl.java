package com.ems.service;

import com.ems.entity.Login;
import com.ems.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private Integer id;
    private String email;
    private String password;
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String role;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Login login, Employee employee) {
        // Determine role based on employee position or a default role system
        String role = determineRole(employee);

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
        );
        System.out.println(authorities);
        return new UserDetailsImpl(
                login.getId(),
                login.getEmail(),
                login.getPasswordHash(),
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                role,
                authorities
        );
    }

    private static String determineRole(Employee employee) {
        // You can customize this logic based on your business rules
        System.out.println(employee.getPosition());
        if (employee.getRole() != null &&
                        employee.getRole().toLowerCase().contains("admin")) {
            return "ADMIN";
        }
        return "EMPLOYEE";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsImpl)) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
