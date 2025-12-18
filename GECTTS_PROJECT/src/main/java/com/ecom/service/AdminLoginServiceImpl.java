package com.ecom.service;

import com.ecom.entities.AdminLogin;
import com.ecom.repositories.AdminLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AdminLoginRepository repo;

    @Override
    public AdminLogin registerAdmin(AdminLogin admin) {
        return repo.save(admin);
    }

    @Override
    public AdminLogin findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public List<AdminLogin> findAll() {
        return repo.findAll();
    }
}
