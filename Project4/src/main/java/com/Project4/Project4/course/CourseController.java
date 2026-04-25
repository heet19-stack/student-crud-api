package com.Project4.Project4.course;


import com.Project4.Project4.course.dto.CourseDTO;
import com.Project4.Project4.course.dto.CourseResponseDTO;
import com.Project4.Project4.dto.StudentResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController
{
    @Autowired
    private final CourseService courseService;

    @PostMapping("/course")
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO){
       return ResponseEntity.ok(courseService.createCourse(courseDTO));
    }
    @GetMapping("/course/{id}")
    public CourseResponseDTO getCourseById(@PathVariable Long id){

       Course course = courseService.getCourseById(id);

      return new CourseResponseDTO(
              course.getId(),
              course.getTitle(),
              course.getDescription()
      );
    }
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<String> assignCourseToStudent(@PathVariable Long studentId,@PathVariable Long courseId){
        courseService.assignCourseToStudent(studentId,courseId);
        return ResponseEntity.ok("Course assigned  to student successfully");
    }
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted!");
    }
    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<String> deleteCourseForStudent(@PathVariable Long studentId, @PathVariable Long courseId){
        courseService.deleteCourseForStudent(studentId,courseId);
        return ResponseEntity.ok("Course deleted for the student : " + studentId);
    }
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<CourseResponseDTO>> courseListOfStudent(@PathVariable Long studentId){
        List<CourseResponseDTO> response = courseService.courseListOfStudent(studentId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentResponseDTO>> studentListOfCourse(@PathVariable Long courseId){
        List<StudentResponseDTO> response = courseService.studentListOfCourse(courseId);
        return ResponseEntity.ok(response);
    }
     @PutMapping("Courses/{courseId}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO){
        return ResponseEntity.ok(courseService.updateCourse(courseId,courseDTO));
     }
}
