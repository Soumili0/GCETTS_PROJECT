package com.ecom.service;

import com.ecom.entities.StudentDetailsLogin;
import com.ecom.repositories.StudentDetailsLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentDetailsLoginServiceImpl implements StudentDetailsLoginService {
    @Autowired
    private StudentDetailsLoginRepository repo;

    @Override
    public StudentDetailsLogin registerStudent(StudentDetailsLogin student) {
        return repo.save(student);
    }

    @Override
    public StudentDetailsLogin findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public StudentDetailsLogin findByRollno(String rollno) {
        return repo.findByRollno(rollno);
    }

    @Override
    public List<StudentDetailsLogin> findAll() {
        return repo.findAll();
    }
}
