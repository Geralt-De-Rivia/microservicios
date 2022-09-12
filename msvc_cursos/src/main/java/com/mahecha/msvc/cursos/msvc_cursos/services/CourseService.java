package com.mahecha.msvc.cursos.msvc_cursos.services;

import com.mahecha.msvc.cursos.msvc_cursos.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findByAllCourse();
    Optional<Course> findCourseById(Long id);
    Course saveCourse(Course course);
    void deleteCourse(Long id);
}
