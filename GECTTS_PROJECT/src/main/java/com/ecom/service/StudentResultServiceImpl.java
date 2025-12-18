package com.ecom.service;

import com.ecom.entities.Student;
import com.ecom.entities.StudentResult;
import com.ecom.repositories.StudentResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentResultServiceImpl implements StudentResultService {
    @Autowired
    private StudentResultRepository studentResultRepository;

    @Override
    public List<StudentResult> getResultsForStudent(Student student) {
        return studentResultRepository.findByStudent(student);
    }

    @Override
    public void addOrUpdateResult(Student student, String subject, Integer marks) {
        // For simplicity, just add new result (can be improved to update if exists)
        StudentResult result = new StudentResult();
        result.setStudent(student);
        result.setSubject(subject);
        result.setMarks(marks);
        studentResultRepository.save(result);
    }
}
