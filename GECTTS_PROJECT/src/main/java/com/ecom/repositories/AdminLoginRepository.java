package com.ecom.repositories;

import com.ecom.entities.AdminLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLoginRepository extends JpaRepository<AdminLogin, Integer> {
    AdminLogin findByEmail(String email);
}
