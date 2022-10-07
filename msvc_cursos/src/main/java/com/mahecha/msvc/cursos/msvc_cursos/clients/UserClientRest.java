package com.mahecha.msvc.cursos.msvc_cursos.clients;

import com.mahecha.msvc.cursos.msvc_cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UserClientRest {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id);

    @PostMapping
    public User save(@RequestBody User user);

    @GetMapping("/usersCourse")
    public List<User> getUsersCourse(@RequestParam Iterable<Long> ids);
}
