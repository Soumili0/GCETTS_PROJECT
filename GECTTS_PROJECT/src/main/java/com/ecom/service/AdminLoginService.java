package com.ecom.service;

import com.ecom.entities.AdminLogin;
import java.util.List;

public interface AdminLoginService {
    AdminLogin registerAdmin(AdminLogin admin);
    AdminLogin findByEmail(String email);
    List<AdminLogin> findAll();
}
