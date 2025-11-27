package com.ems.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "leave_requests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer employeeId;

    @Column(length = 30)
    private String type;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer days;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(length = 20)
    private String status = "PENDING";

    private LocalDate appliedOn;

    @PrePersist
    public void prePersist() {
        appliedOn = LocalDate.now();
    }
}
