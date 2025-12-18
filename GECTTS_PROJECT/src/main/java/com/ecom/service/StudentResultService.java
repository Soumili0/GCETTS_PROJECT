package com.ecom.service;

import com.ecom.entities.Student;
import com.ecom.entities.StudentResult;
import java.util.List;

public interface StudentResultService {
    List<StudentResult> getResultsForStudent(Student student);
    void addOrUpdateResult(Student student, String subject, Integer marks);
}
