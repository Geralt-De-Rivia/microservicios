package com.wilmak.msvc.usuarios.msvc_usuarios.services;

import com.wilmak.msvc.usuarios.msvc_usuarios.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    Optional<User> findUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    List<User> findAllUsersByIds(Iterable<Long> ids);
    Optional<User> findUserByEmail(String email);
}
