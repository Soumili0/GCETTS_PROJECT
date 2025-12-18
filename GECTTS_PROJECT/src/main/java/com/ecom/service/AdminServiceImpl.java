package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.entities.Admin;
import com.ecom.repositories.AdminRepository;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public boolean registerAdmin(Admin admin) {
        try {
            // prevent duplicate email registrations
            List<Admin> existing = adminRepository.findAllByEmail(admin.getEmail());
            if (existing != null && !existing.isEmpty()) {
                // Duplicate email exists — reject registration
                return false;
            }
            // encode password before saving
            if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
            adminRepository.save(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public Admin loginAdmin(String email, String password) {
        List<Admin> admins = adminRepository.findAllByEmail(email);
        if (admins == null || admins.isEmpty()) return null;
        // try to find the one with matching password
        for (Admin admin : admins) {
            if (admin.getPassword() != null && passwordEncoder.matches(password, admin.getPassword())) {
                return admin;
            }
        }
        // none matched
        return null;
    }

    @Override
    public Admin findByEmail(String email) {
        List<Admin> admins = adminRepository.findAllByEmail(email);
        if (admins == null || admins.isEmpty()) return null;
        return admins.get(0);
    }
}
