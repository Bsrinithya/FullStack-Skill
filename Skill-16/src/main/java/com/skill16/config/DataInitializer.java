package com.skill16.config;

import com.skill16.entity.Student;
import com.skill16.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {

            Student s1 = new Student();
            s1.setName("");
            s1.setEmail("alice@college.edu");
            s1.setCourse("Computer Science");

            Student s2 = new Student();
            s2.setName("Bob Smith");
            s2.setEmail("bob@college.edu");
            s2.setCourse("Information Technology");

            Student s3 = new Student();
            s3.setName("Carol White");
            s3.setEmail("carol@college.edu");
            s3.setCourse("Data Science");

            studentRepository.save(s1);
            studentRepository.save(s2);
            studentRepository.save(s3);

            System.out.println("✅ Sample students seeded successfully.");
            System.out.println("📄 Swagger UI →  http://localhost:8080/swagger-ui/index.html");
            System.out.println("🗄  H2 Console →  http://localhost:8080/h2-console");
        }
    }
}