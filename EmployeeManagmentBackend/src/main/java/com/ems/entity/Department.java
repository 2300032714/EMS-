package com.ems.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer managerId;
    private Integer employeeCount;
}
