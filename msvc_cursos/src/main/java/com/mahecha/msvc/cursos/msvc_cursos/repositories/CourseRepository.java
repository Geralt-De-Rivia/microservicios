package com.mahecha.msvc.cursos.msvc_cursos.repositories;

import com.mahecha.msvc.cursos.msvc_cursos.models.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("DELETE FROM CourseUser cu WHERE cu.userId = ?1")
    void deleteCourseUserById(Long id);

}
