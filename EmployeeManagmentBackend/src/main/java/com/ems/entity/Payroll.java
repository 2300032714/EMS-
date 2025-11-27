package com.ems.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer employeeId;

    @Column(length = 20)
    private String month;

    @Column(precision = 10, scale = 2)
    private BigDecimal grossPay;

    @Column(precision = 10, scale = 2)
    private BigDecimal deductions;

    @Column(precision = 10, scale = 2)
    private BigDecimal netPay;

    @Column(length = 20)
    private String status;

    private LocalDate payDate;
}
