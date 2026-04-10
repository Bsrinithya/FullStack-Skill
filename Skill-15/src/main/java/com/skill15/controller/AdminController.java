package com.skill15.controller;

import com.skill15.entity.Employee;
import com.skill15.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * GET /admin/employees  – list all employees (ADMIN only)
     */
    @GetMapping("/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    /**
     * POST /admin/add  – add a new employee record (ADMIN only)
     * Body: { "name": "...", "email": "...", "department": "...", "designation": "..." }
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.ok(Map.of(
                "message",    "Employee added successfully.",
                "employeeId", saved.getId(),
                "name",       saved.getName()
        ));
    }

    /**
     * DELETE /admin/delete/{id}  – delete an employee by ID (ADMIN only)
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepository.existsById(id))
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Employee with id " + id + " not found."));

        employeeRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Employee with id " + id + " deleted successfully."));
    }
}
