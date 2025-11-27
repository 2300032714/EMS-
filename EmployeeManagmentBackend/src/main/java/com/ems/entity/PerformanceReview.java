package com.ems.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "performance_reviews")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer employeeId;

    @Column(length = 20)
    private String period;

    @Column(precision = 3, scale = 2)
    private BigDecimal score;

    @Column(length = 100)
    private String reviewer;

    @Column(columnDefinition = "TEXT")
    private String comments;
}
