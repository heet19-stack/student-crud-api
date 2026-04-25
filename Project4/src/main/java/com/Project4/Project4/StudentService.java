package com.Project4.Project4;

import com.Project4.Project4.dto.StudentDTO;
import com.Project4.Project4.dto.StudentResponseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentService
{
    @Autowired
    private StudentRepository studentRepository;

    public StudentResponseDTO addStudent(StudentDTO studentDTO){
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAge(studentDTO.getAge());

        Student savedstudent = studentRepository.save(student);
        return new StudentResponseDTO(
                savedstudent.getId(),
                savedstudent.getName(),
                savedstudent.getEmail(),
                savedstudent.getAge()
        );
    }

    public StudentResponseDTO mapToDto(Student student){
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setAge(student.getAge());

        return studentResponseDTO;
    }

    public Page<StudentResponseDTO> getAllStudents(Pageable pageable){
       Page<Student> students =  studentRepository.findAll(pageable);

       List<StudentResponseDTO> dtoList = new ArrayList<>();

        for (Student student : students){
            dtoList.add(mapToDto(student));
        }
        return new PageImpl<>(
          dtoList,
          pageable,
          students.getTotalElements()
        );
    }

  public void deleteStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found with id : " + id));
        studentRepository.delete(student);
  }
    public Student getStudentById(Long id){

         return studentRepository.findById(id)
                 .orElseThrow(()->new ResourceNotFoundException("Student not found with id : " + id));
    }

    public StudentResponseDTO updateStudentById(Long id,StudentDTO updatedStudent){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found with id : " + id));

        student.setName(updatedStudent.getName());
        student.setAge(updatedStudent.getAge());
        student.setEmail(updatedStudent.getEmail());

         Student savedStudent = studentRepository.save(student);

         return new StudentResponseDTO (
                 savedStudent.getId(),
                 savedStudent.getName(),
                 savedStudent.getEmail(),
                 savedStudent.getAge()
         );
    }
    public long totalStudents(){
        return studentRepository.count();
    }
    public List<Student> findByEmail(String keyword){
        List<Student> students =  studentRepository.findByEmailContaining(keyword);

        if (students.isEmpty()){
            throw new ResourceNotFoundException("No student found with email : " + keyword);
        }
        return students;
    }
    List<Student> findByStudentName(String name){
        List<Student> students = studentRepository.findByNameContaining(name);

        if (students.isEmpty()){
            throw new ResourceNotFoundException("No student found with name : " + name);
        }
        return students;
    }
    public Page<Student> searchStudentsPaged(String keyword,Pageable pageable){
        return studentRepository.findByEmailContaining(keyword,pageable);
    }
    public List<Student> getStudentsOlderThan(int age){
        return studentRepository.findStudentOlderThan(age);
    }
}