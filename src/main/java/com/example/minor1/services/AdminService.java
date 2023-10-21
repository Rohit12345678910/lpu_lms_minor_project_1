package com.example.minor1.services;

import com.example.minor1.domain.Admin;
import com.example.minor1.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public void createAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public Admin find(int adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }
}
