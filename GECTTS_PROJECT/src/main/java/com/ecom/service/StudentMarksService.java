package com.ecom.service;

import com.ecom.entities.StudentMarks;
import java.util.List;

public interface StudentMarksService {
    StudentMarks saveOrUpdate(StudentMarks marks);
    StudentMarks findByRollNo(String rollNo);
    StudentMarks findByRollNoAndSubject(String rollNo, String subject);
    List<StudentMarks> findAll();
}
