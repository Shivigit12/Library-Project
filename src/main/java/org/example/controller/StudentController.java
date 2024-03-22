package org.example.controller;

import org.example.dto.CreateStudentRequest;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity createStudent(@RequestBody CreateStudentRequest createStudentRequest) {
        if(createStudentRequest == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else if(studentService.exists(createStudentRequest.getContact())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already exists");
        }
        studentService.create(createStudentRequest);
        return new ResponseEntity<>("student created", HttpStatus.OK);
    }
    @GetMapping("/getStudent/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable int studentId) {
        Student student = studentService.get(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }
    @PutMapping("/update")
    public ResponseEntity updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
        return new ResponseEntity<>("Student updated", HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
