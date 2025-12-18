package com.ecom.service;

import com.ecom.entities.Admin;

public interface AdminService {
    boolean registerAdmin(Admin admin);
    Admin loginAdmin(String email, String password);
    Admin findByEmail(String email);
}