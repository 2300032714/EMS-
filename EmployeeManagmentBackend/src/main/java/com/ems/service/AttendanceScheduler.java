package com.ems.service;

import com.ems.entity.Attendance;
import com.ems.entity.Employee;
import com.ems.repository.AttendanceRepository;
import com.ems.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceScheduler {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    private static final int BATCH_SIZE = 10000;

    @Transactional
    @Scheduled(cron = "0 30 20 * * *") // runs daily at 10:06 AM
    public void createDailyAbsentRecords() {
        LocalDate today = LocalDate.now();

        List<Employee> employees = employeeRepository.findAll();
        List<Attendance> batch = new ArrayList<>();

        for (Employee emp : employees) {

            boolean exists = attendanceRepository.findByEmployeeAndDate(emp, today).isPresent();

            if (!exists) {
                Attendance attendance = Attendance.builder()
                        .employee(emp)
                        .date(today)
                        .status("Absent")
                        .build();

                batch.add(attendance);
            }

            if (batch.size() >= BATCH_SIZE) {
                attendanceRepository.saveAll(batch);
                attendanceRepository.flush();
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            attendanceRepository.saveAll(batch);
            attendanceRepository.flush();
        }

        System.out.println("Daily absent records created successfully for " + today);
    }
}
