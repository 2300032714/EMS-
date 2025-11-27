package com.ems.controller;

import com.ems.entity.Attendance;
import com.ems.entity.Employee;
import com.ems.repository.AttendanceRepository;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    @PutMapping("/addcheckin/{employeeId}")
    public ResponseEntity<String> addCheckIn(@PathVariable Integer employeeId) {
        System.out.println("attdance");
        LocalDate today = LocalDate.now();

        // 1️⃣ Fetch employee
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        System.out.println("check-1");
//        if (empOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body("Employee not found");
//        }
//        Employee employee = empOpt.get();

        System.out.println(employee.getEmployeeId());
        // 2️⃣ Fetch today's attendance
        Optional<Attendance> attendanceOpt = attendanceRepository.findByEmployeeAndDate(employee, today);
        System.out.println(attendanceOpt);
        Attendance attendance;
        if (attendanceOpt.isPresent()) {
            // Already has an attendance record
            System.out.println("ispre");
            attendance = attendanceOpt.get();
            // Update status to Present
            attendance.setStatus("Present");
            attendanceRepository.save(attendance);
            return ResponseEntity.ok("Check-in updated successfully for employee " + employeeId);
        } else {
            // No attendance record exists for today, create one
            System.out.println("ispre11");
            attendance = Attendance.builder()
                    .employee(employee)
                    .date(today)
                    .status("Present")
                    .createdAt(LocalDateTime.now())
                    .build();
            attendanceRepository.save(attendance);
            return ResponseEntity.ok("Check-in added successfully for employee " + employeeId);
        }
    }
}
