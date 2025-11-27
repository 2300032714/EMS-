package com.ems.repository;

import com.ems.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Integer> {

    List<PerformanceReview> findByEmployeeId(Integer employeeId);

    List<PerformanceReview> findByReviewer(String reviewer);
}
