package com.Project4.Project4;

import com.Project4.Project4.dto.StudentDTO;
import com.Project4.Project4.dto.StudentResponseDTO;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RestController
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<Page<StudentResponseDTO>> getStudents(Pageable pageable){

        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }
    @PostMapping("/students")
    public ResponseEntity<StudentResponseDTO> addStudent(@Valid @RequestBody StudentDTO studentDTO){

            return ResponseEntity.ok(studentService.addStudent(studentDTO));
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
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id,@Valid @RequestBody StudentDTO student){
       return ResponseEntity.ok(studentService.updateStudentById(id,student));

    }
    @GetMapping("/students/count")
    public long getStudents(){
        return studentService.totalStudents();
    }
    @GetMapping("/students/email")
    public List<Student> findByEmail(@RequestParam String keyword){
        return studentService.findByEmail(keyword);
    }
    @GetMapping("/students/name")
    public List<Student> findByStudentName(@RequestParam String name){
        return studentService.findByStudentName(name);
    }
    @GetMapping("/students/search/paged")
    public Page<Student> searcStudentsPaged(@RequestParam String keyword,Pageable pageable){
        return studentService.searchStudentsPaged(keyword,pageable);
    }
    @GetMapping("/students/older")
    public List<Student> getStudentOlder(int age){
        return studentService.getStudentsOlderThan(age);
    }
}