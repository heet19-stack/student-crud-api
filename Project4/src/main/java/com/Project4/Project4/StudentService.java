package com.Project4.Project4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService
{
    @Autowired
    private StudentRepository studentRepository;

    public boolean addStudent(Student student){

        boolean isAdded = true;
                if(isAdded) {
                    studentRepository.save(student);
                    return true;
                }
        return false;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

  public void deleteStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student not found with id : " + id));
        studentRepository.delete(student);
  }
    public Student getStudentById(Long id){

         return studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found with id : " + id));
    }

    public void updateStudentById(Long id,Student updatedstudent){
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student not found with id : " + id));
        student.setName(updatedstudent.getName());
        student.setAge(updatedstudent.getAge());

        studentRepository.save(student);
    }
}