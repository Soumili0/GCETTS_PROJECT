package com.ecom.service;

import com.ecom.entities.StudentDetailsLogin;
import java.util.List;

public interface StudentDetailsLoginService {
    StudentDetailsLogin registerStudent(StudentDetailsLogin student);
    StudentDetailsLogin findByEmail(String email);
    StudentDetailsLogin findByRollno(String rollno);
    List<StudentDetailsLogin> findAll();
}
