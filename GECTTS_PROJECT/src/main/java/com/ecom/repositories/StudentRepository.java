
package com.ecom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entities.Teacher;
import com.ecom.entities.Student;
import java.util.List;




public interface StudentRepository extends JpaRepository<Student,Integer> {

    Student findByRollno(String rollno);

    //void save(com.ecom.entities.User user);
    //User findByEmail(String email);

    Student findByEmail(String email);

    // Search by name or email
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    // Find by fee paid status
    List<Student> findByFeesPaid(boolean feesPaid);

}
