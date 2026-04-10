package com.skill15.controller;

import com.skill15.entity.Employee;
import com.skill15.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * GET /employee/profile  – returns the logged-in user's name & role (EMPLOYEE or ADMIN)
     */
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String username = authentication.getName();
        String role     = authentication.getAuthorities().iterator().next().getAuthority();

        return ResponseEntity.ok(Map.of(
                "username", username,
                "role",     role,
                "message",  "Profile fetched successfully."
        ));
    }

    /**
     * GET /employee/list  – list all employees (EMPLOYEE or ADMIN)
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<Employee>> listEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }
}
