
package com.ecom.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.entities.Student;
import com.ecom.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
        @Override
        public Student findByEmail(String email) {
            return userRepository.findByEmail(email);
        }
    @Override
    public Student findByRollnoAndPassword(String rollno, String password) {
        Student user = userRepository.findByRollno(rollno);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Autowired
    private StudentRepository userRepository;

    @Override
    public boolean registerUser(Student user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Student> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Student findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateStudent(Student student) {
        try {
            userRepository.save(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student loginUser(String email, String password) {
        // Find user by email
        Student user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        // Invalid login
        return null;
    }
    @Override
    public Student findByRollno(String rollno) {
        return userRepository.findByRollno(rollno);
    }
    @Override
    public List<Student> searchStudents(String query, String feeStatus) {
        // Normalize
        String q = (query == null) ? null : query.trim();
        boolean hasQuery = (q != null && !q.isEmpty());
        boolean hasFee = (feeStatus != null && !feeStatus.isEmpty());

        // Case 1: only fee filter
        if (!hasQuery && hasFee) {
            if ("paid".equalsIgnoreCase(feeStatus)) {
                return userRepository.findByFeesPaid(true);
            } else if ("unpaid".equalsIgnoreCase(feeStatus)) {
                return userRepository.findByFeesPaid(false);
            } else {
                return userRepository.findAll();
            }
        }

        // Case 2: only query
        if (hasQuery && !hasFee) {
            return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q);
        }

        // Case 3: both query and fee filter
        if (hasQuery && hasFee) {
            List<Student> byQuery = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q);
            boolean paid = "paid".equalsIgnoreCase(feeStatus);
            return byQuery.stream().filter(s -> s.isFeesPaid() == paid).toList();
        }

        // Default: return all
        return userRepository.findAll();
    }
}
