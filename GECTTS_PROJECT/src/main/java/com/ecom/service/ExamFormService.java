package com.ecom.service;

import com.ecom.entities.ExamForm;
import java.util.List;

public interface ExamFormService {
    ExamForm submitForm(ExamForm form);
    List<ExamForm> getFormsByStudentEmail(String email);
    ExamForm getFormByStudentEmailAndSemester(String email, String semester);
}
