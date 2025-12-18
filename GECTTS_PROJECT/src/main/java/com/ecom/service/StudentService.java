
    package com.ecom.service;

import com.ecom.entities.Teacher;
import com.ecom.entities.Student;

import java.util.List;

public interface StudentService {
            // Find student by roll number
            Student findByRollno(String rollno);
        // Find student by email
        Student findByEmail(String email);
    public boolean registerUser(Student user);
    public List<Student> findAll();
    public Student findById(int id);
    public boolean updateStudent(Student student);
    public boolean deleteStudent(int id);
    public Student loginUser(String email, String password);

    // Find student by roll number and password
    Student findByRollnoAndPassword(String rollno, String password);

    // Search students by query (name or email) and optional fee status ("paid","unpaid","all"/null)
    public List<Student> searchStudents(String query, String feeStatus);
}
