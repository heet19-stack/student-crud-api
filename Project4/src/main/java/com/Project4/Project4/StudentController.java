package com.Project4.Project4;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentService.getAllStudents();
    }
    @PostMapping("/students")
    public ResponseEntity<String> addStudent(@Valid @RequestBody Student student){
        boolean isAdded = studentService.addStudent(student);

        if (isAdded){
            return new ResponseEntity<>("Student added successfully!",HttpStatus.OK);
        }
        return new ResponseEntity<>("Student was not added!",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudentById(id);
            return new ResponseEntity<>("Student deleted successfully!",HttpStatus.OK);
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
            return new ResponseEntity<>(student,HttpStatus.OK);
    }
    @PutMapping("/students/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id,@Valid @RequestBody Student student){
        studentService.updateStudentById(id,student);
        return new ResponseEntity<>("Student data updated successfuly!",HttpStatus.OK);
    }
}