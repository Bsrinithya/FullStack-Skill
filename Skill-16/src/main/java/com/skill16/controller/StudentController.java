package com.skill16.controller;

import com.skill16.entity.Student;
import com.skill16.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@Tag(name = "Student Management", description = "APIs for managing student records — Add, Retrieve, Update, Delete")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ─── POST /students ────────────────────────────────────────────────────────

    @Operation(
        summary     = "Add a new student",
        description = "Creates a new student record. All fields (name, email, course) are required. Email must be unique."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student added successfully",
            content = @Content(schema = @Schema(implementation = Student.class))),
        @ApiResponse(responseCode = "400", description = "Validation error — missing or invalid fields",
            content = @Content(examples = @ExampleObject(value = "{\"error\": \"Name is required\"}")))
    })
    @PostMapping
    public ResponseEntity<Student> addStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Student object to be added",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Student.class),
                    examples = @ExampleObject(
                        name = "Sample Student",
                        value = "{\"name\": \"Alice Johnson\", \"email\": \"alice@college.edu\", \"course\": \"Computer Science\"}"
                    )
                )
            )
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    // ─── GET /students ─────────────────────────────────────────────────────────

    @Operation(
        summary     = "Get all students",
        description = "Retrieves a list of all student records stored in the database."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of students retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    })
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ─── GET /students/{id} ────────────────────────────────────────────────────

    @Operation(
        summary     = "Get student by ID",
        description = "Retrieves a single student record using their unique ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student found",
            content = @Content(schema = @Schema(implementation = Student.class))),
        @ApiResponse(responseCode = "404", description = "Student not found — invalid ID",
            content = @Content(examples = @ExampleObject(value = "{\"message\": \"Student with id 999 not found.\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(
            @Parameter(description = "Unique ID of the student", example = "1")
            @PathVariable Long id) {
        return studentService.getStudentById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404)
                        .body(Map.of("message", "Student with id " + id + " not found.")));
    }

    // ─── PUT /students/{id} ────────────────────────────────────────────────────

    @Operation(
        summary     = "Update a student",
        description = "Updates an existing student record by ID. All fields will be overwritten with the provided values."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student updated successfully",
            content = @Content(schema = @Schema(implementation = Student.class))),
        @ApiResponse(responseCode = "404", description = "Student not found",
            content = @Content(examples = @ExampleObject(value = "{\"message\": \"Student with id 999 not found.\"}")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @Parameter(description = "Unique ID of the student to update", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated student data",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Student.class),
                    examples = @ExampleObject(
                        value = "{\"name\": \"Alice Updated\", \"email\": \"alice_new@college.edu\", \"course\": \"Data Science\"}"
                    )
                )
            )
            @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404)
                        .body(Map.of("message", "Student with id " + id + " not found.")));
    }

    // ─── DELETE /students/{id} ─────────────────────────────────────────────────

    @Operation(
        summary     = "Delete a student",
        description = "Deletes a student record by their unique ID. Returns 404 if the ID does not exist."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student deleted successfully",
            content = @Content(examples = @ExampleObject(value = "{\"message\": \"Student with id 1 deleted successfully.\"}"))),
        @ApiResponse(responseCode = "404", description = "Student not found",
            content = @Content(examples = @ExampleObject(value = "{\"message\": \"Student with id 999 not found.\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(
            @Parameter(description = "Unique ID of the student to delete", example = "1")
            @PathVariable Long id) {
        if (studentService.deleteStudent(id))
            return ResponseEntity.ok(Map.of("message", "Student with id " + id + " deleted successfully."));
        return ResponseEntity.status(404)
                .body(Map.of("message", "Student with id " + id + " not found."));
    }
}
