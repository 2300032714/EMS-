package com.ems.repository;

import com.ems.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer> {

    List<Payroll> findByEmployeeId(Integer employeeId);

    List<Payroll> findByStatus(String status);
}
