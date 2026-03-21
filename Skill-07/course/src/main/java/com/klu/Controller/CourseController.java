package com.klu.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.model.Course;
import com.klu.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCourse(c));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(service.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Optional<Course> x = service.getCourseById(id);

        if (x.isPresent()) return ResponseEntity.ok(x.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course c) {
        try {
            return ResponseEntity.ok(service.updateCourse(id, c));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            service.deleteCourse(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> search(@PathVariable String title) {
        List<Course> x = service.searchByTitle(title);

        if (x.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Data");
        else return ResponseEntity.ok(x);
    }
}