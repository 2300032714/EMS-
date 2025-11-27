package com.ems.controller;

import com.ems.dto.LeaveRequestDto;
import com.ems.entity.LeaveRequest;
import com.ems.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveRequestController {

    private final LeaveRequestRepository leaveRequestRepository;

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestRepository.findAll());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getEmployeeLeaveRequests(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(leaveRequestRepository.findByEmployeeId(employeeId));
    }

    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestBody LeaveRequestDto dto) {
        LeaveRequest request = new LeaveRequest();
        request.setEmployeeId(dto.getEmployeeId());
        request.setType(dto.getType());
        request.setStartDate(dto.getStartDate());
        request.setEndDate(dto.getEndDate());
        request.setDays(dto.getDays());
        request.setReason(dto.getReason());
        request.setStatus("PENDING");
        request.setAppliedOn(LocalDate.now());

        LeaveRequest saved = leaveRequestRepository.save(request);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Integer id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        request.setStatus("APPROVED");
        return ResponseEntity.ok(leaveRequestRepository.save(request));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Integer id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        request.setStatus("REJECTED");
        return ResponseEntity.ok(leaveRequestRepository.save(request));
    }
}
