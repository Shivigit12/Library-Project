package org.example.service;

import org.example.dto.CreateStudentRequest;
import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public boolean exists(String email) {
        // Check if student with the given ID exists in the database
        Optional<Student> existingStudent = studentRepository.findByEmail(email);
        return existingStudent.isPresent();
    }
    public Student create(CreateStudentRequest createStudentRequest) {
        Student student = createStudentRequest.to();
        return studentRepository.save(student);
    }
    public Student get(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public List<Student> getAll() {
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    public void updateStudent(Student student) {
        Student student1 = studentRepository.findById(student.getStudentId())
                .orElseThrow(()-> new RuntimeException("Student id not found"));
        student1.setName(student.getName());
        student1.setStudentId(student.getStudentId());
        student1.setAge(student.getAge());
        student1.setCountry(student.getCountry());
        student1.setEmail(student.getEmail());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setUpdatedOn(student.getUpdatedOn());
        studentRepository.save(student1);

    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);

    }


}
