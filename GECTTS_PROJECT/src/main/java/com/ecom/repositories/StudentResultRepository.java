package com.ecom.repositories;

import com.ecom.entities.Student;
import com.ecom.entities.StudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {
    List<StudentResult> findByStudent(Student student);
}
