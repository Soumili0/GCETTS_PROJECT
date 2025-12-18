package com.ecom.repositories;

import com.ecom.entities.StudentDetailsLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDetailsLoginRepository extends JpaRepository<StudentDetailsLogin, Integer> {
    StudentDetailsLogin findByEmail(String email);
    StudentDetailsLogin findByRollno(String rollno);
}
