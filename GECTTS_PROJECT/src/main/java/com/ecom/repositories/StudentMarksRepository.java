package com.ecom.repositories;

import com.ecom.entities.StudentMarks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentMarksRepository extends JpaRepository<StudentMarks, Long> {
    StudentMarks findByRollNo(String rollNo);
    StudentMarks findByRollNoAndSubject(String rollNo, String subject);
}
