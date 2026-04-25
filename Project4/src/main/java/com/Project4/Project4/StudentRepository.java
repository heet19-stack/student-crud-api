package com.Project4.Project4;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository  extends JpaRepository<Student,Long> {
    List<Student> findByEmailContaining(String keyword);
    List<Student> findByNameContaining(String name);
    Page<Student> findByEmailContaining(String keyword, Pageable pageable);
    @Query("Select s From Student s WHERE s.age > :age")
    public List<Student> findStudentOlderThan(@Param("age") int age);
}
