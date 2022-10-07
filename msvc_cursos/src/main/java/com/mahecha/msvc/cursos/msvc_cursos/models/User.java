package com.mahecha.msvc.cursos.msvc_cursos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long user_id;

    private String name;

    private String email;

    private String password;
}
