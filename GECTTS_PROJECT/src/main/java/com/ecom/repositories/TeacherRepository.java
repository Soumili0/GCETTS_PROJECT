package com.ecom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecom.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Teacher findByEmail(String email);
}
