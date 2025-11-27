package com.ems.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeRequest {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer departmentId;
    private String position;
    private Double salary;
    private LocalDate hireDate;
    private Integer managerId;
    private String role;
}
