package org.example.controller;

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
    public ResponseEntity createStudent(@RequestBody Student student) {
        if(student == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else if(studentService.exists(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already exists");
        }
        studentService.create(student);
        return new ResponseEntity<>("student created", HttpStatus.OK);
    }
    @GetMapping("/getStudents")
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
