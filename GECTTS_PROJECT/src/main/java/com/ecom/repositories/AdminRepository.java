package com.ecom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecom.entities.Admin;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
    Admin findByEmailAndPhone(String email, String phone);
    Admin findByEmailAndPhoneAndPassword(String email, String phone, String password);

    // Return all admins with same email (safe for duplicate data)
    List<Admin> findAllByEmail(String email);
}
