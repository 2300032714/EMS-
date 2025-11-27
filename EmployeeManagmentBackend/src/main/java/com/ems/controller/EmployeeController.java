package com.ems.controller;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        // Convert salary from Double to BigDecimal if necessary
        if (employee.getSalary() != null) {
            employee.setSalary(BigDecimal.valueOf(employee.getSalary().doubleValue()));
        }
        return employeeRepository.save(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Employee employee = optionalEmployee.get();
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setDepartmentId(updatedEmployee.getDepartmentId());
        employee.setPosition(updatedEmployee.getPosition());
        employee.setRole(updatedEmployee.getRole());
        employee.setManagerId(updatedEmployee.getManagerId());

        // Convert salary to BigDecimal
        if (updatedEmployee.getSalary() != null) {
            employee.setSalary(BigDecimal.valueOf(updatedEmployee.getSalary().doubleValue()));
        }

        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
