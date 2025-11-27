package com.ems.repository;

import com.ems.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {

    Optional<Login> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Login> findByEmployeeId(Integer employeeId);
}
