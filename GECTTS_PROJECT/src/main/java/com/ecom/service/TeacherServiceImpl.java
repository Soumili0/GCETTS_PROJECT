package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.entities.Teacher;
import com.ecom.repositories.TeacherRepository;

import java.util.List;
/*
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public boolean registerUser(Teacher teacher) {
        teacherRepository.save(teacher);
        return true;
    }

    @Override
    public Teacher loginUser(String email, String password) {
        Teacher teacher = teacherRepository.findByEmail(email);

        if (teacher != null && teacher.getPassword().equals(password)) {
            return teacher;
        }
        return null;
    }
}*/
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
    private TeacherRepository userRepository;

    @Override
    public boolean registerTeacher(Teacher user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Teacher> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Teacher findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        try {
            userRepository.save(teacher);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTeacher(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Teacher loginUser(String email, String password) {
        // Find user by email
        Teacher user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        // Invalid login
        return null;
    }
}

