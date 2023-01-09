package com.mahecha.msvc.cursos.msvc_cursos.clients;

import com.mahecha.msvc.cursos.msvc_cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
public interface UserClientRest {

    @GetMapping("/{id}")
    User getUser(@PathVariable Long id);

    @PostMapping
    User save(@RequestBody User user);

    @GetMapping("/usersCourse")
    List<User> getUsersCourse(@RequestParam Iterable<Long> ids);
}
