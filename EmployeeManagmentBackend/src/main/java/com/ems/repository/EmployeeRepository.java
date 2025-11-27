package com.ems.repository;

import com.ems.entity.Employee;
import com.ems.entity.Employee.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmployeeId(Integer employeeId);

//    Optional<Employee> findById(Integer id);



    Optional<Employee> findByEmail(String email);

    Page<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);

    Page<Employee> findByDepartmentId(Integer departmentId, Pageable pageable);

    List<Employee> findByStatus(EmployeeStatus status);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.status = 'ACTIVE'")
    long countActiveEmployees();
}
