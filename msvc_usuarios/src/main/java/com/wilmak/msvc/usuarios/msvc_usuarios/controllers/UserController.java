package com.wilmak.msvc.usuarios.msvc_usuarios.controllers;

import com.wilmak.msvc.usuarios.msvc_usuarios.models.User;
import com.wilmak.msvc.usuarios.msvc_usuarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            return validators(result);
        }

        if (userService.findUserByEmail(user.getEmail()).isPresent()) {
            return validatorExistsEmail();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encodedPassword = encoder.encode(user.getPassword());
        //String encodedPassword2 = encoder.encode(user.getPassword2());
        /*if (!encoder.matches(user.getPassword(), encodedPassword2)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password no coincide");
        }*/
        user.setPassword(encodedPassword);
        //user.setPassword2(encodedPassword2);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validators(result);
        }

        Optional<User> userOptional = userService.findUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

        User userId = userOptional.get();
        if (!user.getEmail().equalsIgnoreCase(userId.getEmail())
                && userService.findUserByEmail(user.getEmail()).isPresent()) {
            return validatorExistsEmail();
        }
        userId.setName(user.getName());
        String encodedPassword = encoder.encode(user.getPassword());
        userId.setPassword(encodedPassword);
        userId.setEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> userOptional = userService.findUserById(id);
        if (userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usersCourse")
    public ResponseEntity<?> getUsersCourse(@RequestParam List<Long> ids){
        return ResponseEntity.ok(userService.findAllUsersByIds(ids));
    }

    private ResponseEntity<Map<String, String>> validators(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    private ResponseEntity<Map<String, String>> validatorExistsEmail() {
        return ResponseEntity.badRequest().body(Collections.singletonMap(
                "mensaje", "Ya existe un usuario con ese Email"
        ));
    }
}
