package com.ecom.repositories;

import com.ecom.entities.ExamForm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamFormRepository extends JpaRepository<ExamForm, Long> {
    List<ExamForm> findByStudentEmail(String studentEmail);
    ExamForm findByStudentEmailAndSemester(String studentEmail, String semester);
}
