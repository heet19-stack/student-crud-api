package com.Project4.Project4.course;


import com.Project4.Project4.Student;
import com.Project4.Project4.StudentRepository;
import com.Project4.Project4.course.dto.CourseDTO;
import com.Project4.Project4.course.dto.CourseResponseDTO;
import com.Project4.Project4.course.exception.StudentNotFoundException;
import com.Project4.Project4.dto.StudentResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService
{   @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final StudentRepository studentRepository;

    public CourseResponseDTO createCourse(CourseDTO courseDTO){
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());

        Course savedcourse = courseRepository.save(course);
        return new CourseResponseDTO(
                savedcourse.getId(),
                savedcourse.getTitle(),
                savedcourse.getDescription()
        );
    }
    public Course getCourseById(Long courseId){
       return courseRepository.findById(courseId)
               .orElseThrow(()-> new StudentNotFoundException("Course not found with id : " + courseId));

    }
    public void assignCourseToStudent(Long studentId,Long courseId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new StudentNotFoundException("Student not found with id : " + studentId));

        Course course = getCourseById(courseId);

        if (student.getCourses() == null){
            student.setCourses(new ArrayList<>());
        }
        if (!student.getCourses().contains(course)){
            student.getCourses().add(course);
            course.getStudents().add(student);
        }
        studentRepository.save(student);
    }
    public void deleteCourse(Long courseId){
        Course course = getCourseById(courseId);
       for (Student student : course.getStudents()){
           student.getCourses().remove(course);
       }
        courseRepository.delete(course);
    }

    public void deleteCourseForStudent(Long studentId,Long courseId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new StudentNotFoundException("Student not found with id : " + studentId));

        Course course = getCourseById(courseId);

        course.getStudents().remove(student);
        student.getCourses().remove(course);

        studentRepository.save(student);
    }

    @Transactional
    public List<CourseResponseDTO> courseListOfStudent(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new StudentNotFoundException("Student not found with id : " + studentId));

       List<CourseResponseDTO> responseList = new ArrayList<>();

       for (Course course : student.getCourses()){
           CourseResponseDTO dto = new CourseResponseDTO(
                   course.getId(),
                   course.getTitle(),
                   course.getDescription()
           );
           responseList.add(dto);
       }
       return responseList;
    }

    public List<StudentResponseDTO> studentListOfCourse(Long courseId){
        Course course = getCourseById(courseId);

        List<StudentResponseDTO> studentResponseList = new ArrayList<>();

        for (Student student : course.getStudents()){
            StudentResponseDTO dto = new StudentResponseDTO(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getAge()
            );
            studentResponseList.add(dto);
        }
      return studentResponseList;
    }

    public CourseResponseDTO updateCourse(Long courseId,CourseDTO courseDTO){
        Course course = getCourseById(courseId);

        course.setTitle(course.getTitle());
        course.setDescription(courseDTO.getDescription());

        Course updatedCourse = courseRepository.save(course);

        return new CourseResponseDTO(
                updatedCourse.getId(),
                updatedCourse.getTitle(),
                updatedCourse.getDescription()
        );
    }
}