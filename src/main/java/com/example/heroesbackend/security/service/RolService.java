package com.example.heroesbackend.security.service;

import com.example.heroesbackend.security.entity.Role;
import com.example.heroesbackend.security.enums.RolName;
import com.example.heroesbackend.security.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {
    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Optional<Role> getRolByName(RolName rolName) {
        return rolRepository.findByRolName(rolName);
    }

    public void save(Role rol) {
        rolRepository.save(rol);
    }
}
