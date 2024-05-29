package com.example.heroesbackend.security.repository;

import com.example.heroesbackend.security.entity.Role;
import com.example.heroesbackend.security.enums.RolName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByRolName(RolName rolName);
}
