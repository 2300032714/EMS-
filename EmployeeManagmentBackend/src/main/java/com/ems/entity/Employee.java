package com.ems.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer departmentId;
    private String position;

    @Column(precision = 15, scale = 2)
    private java.math.BigDecimal salary;

    private LocalDate hireDate;
    private Integer managerId;
    private String role;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private Date createdAt;
    private Date updatedAt;

    public enum EmployeeStatus {
        ACTIVE,
        INACTIVE,
        TERMINATED
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }
}
