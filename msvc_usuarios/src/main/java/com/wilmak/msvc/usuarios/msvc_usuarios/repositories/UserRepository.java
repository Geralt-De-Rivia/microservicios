package com.wilmak.msvc.usuarios.msvc_usuarios.repositories;

import com.wilmak.msvc.usuarios.msvc_usuarios.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
