package com.ecom.service;

import com.ecom.entities.Teacher;

import java.util.List;

public interface TeacherService {

    boolean registerTeacher(Teacher teacher);

    Teacher loginUser(String email, String password);

    List<Teacher> findAll();
    Teacher findById(int id);
    boolean updateTeacher(Teacher teacher);
    boolean deleteTeacher(int id);
}
