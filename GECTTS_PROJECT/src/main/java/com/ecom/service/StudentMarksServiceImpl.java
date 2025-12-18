package com.ecom.service;

import com.ecom.entities.StudentMarks;
import com.ecom.repositories.StudentMarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {
    @Autowired
    private StudentMarksRepository repo;

    @Override
    public StudentMarks saveOrUpdate(StudentMarks marks) {
        // Overwrite if same student and subject exists
        if (marks.getRollNo() != null && marks.getSubject() != null) {
            StudentMarks existing = repo.findByRollNoAndSubject(marks.getRollNo(), marks.getSubject());
            if (existing != null) {
                // Update existing record
                existing.setMarksObtained(marks.getMarksObtained());
                existing.setTotalMarks(marks.getTotalMarks());
                existing.setTotalClass(marks.getTotalClass());
                existing.setAttendance(marks.getAttendance());
                existing.setDepartment(marks.getDepartment());
                existing.setStream(marks.getStream());
                return repo.save(existing);
            }
        }
        return repo.save(marks);
    }

    @Override
    public StudentMarks findByRollNo(String rollNo) {
        return repo.findByRollNo(rollNo);
    }

    @Override
    public StudentMarks findByRollNoAndSubject(String rollNo, String subject) {
        return repo.findByRollNoAndSubject(rollNo, subject);
    }

    @Override
    public List<StudentMarks> findAll() {
        return repo.findAll();
    }
}
