package com.mahecha.msvc.cursos.msvc_cursos.repositories;

import com.mahecha.msvc.cursos.msvc_cursos.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
