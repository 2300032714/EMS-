package com.ems.repository;

import com.ems.entity.LeaveRequest;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Registered
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findByEmployeeId(Integer employeeId);

    List<LeaveRequest> findByStatus(String status);

    long countByStatus(String status);
}
