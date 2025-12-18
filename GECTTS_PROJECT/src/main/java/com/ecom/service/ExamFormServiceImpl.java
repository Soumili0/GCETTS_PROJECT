package com.ecom.service;

import com.ecom.entities.ExamForm;
import com.ecom.repositories.ExamFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExamFormServiceImpl implements ExamFormService {
    @Autowired
    private ExamFormRepository repo;

    @Override
    public ExamForm submitForm(ExamForm form) {
        return repo.save(form);
    }

    @Override
    public List<ExamForm> getFormsByStudentEmail(String email) {
        return repo.findByStudentEmail(email);
    }

    @Override
    public ExamForm getFormByStudentEmailAndSemester(String email, String semester) {
        return repo.findByStudentEmailAndSemester(email, semester);
    }
}
