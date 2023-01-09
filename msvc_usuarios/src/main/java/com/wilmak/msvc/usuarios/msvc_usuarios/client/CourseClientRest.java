package com.wilmak.msvc.usuarios.msvc_usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "${msvc.cursos.url}")
public interface CourseClientRest {

    @DeleteMapping("/deleteCourseUser/{user_id}")
    public void deleteCourseUserById(@PathVariable Long user_id);
}
