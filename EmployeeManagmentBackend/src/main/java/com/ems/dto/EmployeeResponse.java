package com.ems.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Date;

@Data
public class EmployeeResponse {
    private Integer id;
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
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
