package com.ems.controller;

import com.ems.repository.AttendanceRepository;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalEmployees = employeeRepository.count();
        long activeEmployees = employeeRepository.countActiveEmployees();
        long presentToday = attendanceRepository.countPresentByDate(LocalDate.now());

        stats.put("totalEmployees", totalEmployees);
        stats.put("activeEmployees", activeEmployees);
        stats.put("presentToday", presentToday);

        return ResponseEntity.ok(stats);
    }
}
