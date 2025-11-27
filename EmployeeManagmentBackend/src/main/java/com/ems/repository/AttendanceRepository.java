package com.ems.repository;

import com.ems.entity.Attendance;
import com.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    // Find attendance by Employee and date
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    // Find all attendance records for an Employee between two dates
    List<Attendance> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);

    // Find all attendance records on a specific date
    List<Attendance> findByDate(LocalDate date);

    // Count how many employees are present on a specific date
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :date AND a.status = 'Present'")
    long countPresentByDate(LocalDate date);
}
