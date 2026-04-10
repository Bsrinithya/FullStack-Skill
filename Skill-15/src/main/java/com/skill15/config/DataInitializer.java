package com.skill15.config;

import com.skill15.entity.Employee;
import com.skill15.entity.User;
import com.skill15.repository.EmployeeRepository;
import com.skill15.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component 
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // ── Seed ADMIN user ─────────────────────────────────────
        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");

            userRepository.save(admin);

            System.out.println("✅ Default ADMIN user created → username: admin | password: admin123");
        }

        // ── Seed EMPLOYEE user ──────────────────────────────────
        if (userRepository.findByUsername("employee").isEmpty()) {

            User emp = new User();
            emp.setUsername("employee");
            emp.setPassword(passwordEncoder.encode("emp123"));
            emp.setRole("ROLE_EMPLOYEE");

            userRepository.save(emp);

            System.out.println("✅ Default EMPLOYEE user created → username: employee | password: emp123");
        }

        // ── Seed sample employees ───────────────────────────────
        if (employeeRepository.count() == 0) {

            Employee e1 = new Employee();
            e1.setName("Alice Johnson");
            e1.setEmail("alice@company.com");
            e1.setDepartment("Engineering");
            e1.setDesignation("Senior Developer");

            Employee e2 = new Employee();
            e2.setName("Bob Smith");
            e2.setEmail("bob@company.com");
            e2.setDepartment("HR");
            e2.setDesignation("HR Manager");

            employeeRepository.save(e1);
            employeeRepository.save(e2);

            System.out.println("✅ Sample employees seeded.");
        }
    }
}