package com.ems.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestDto {
    private Integer employeeId;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer days;
    private String reason;
}
