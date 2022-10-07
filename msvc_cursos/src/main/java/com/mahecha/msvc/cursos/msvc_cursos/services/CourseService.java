package com.mahecha.msvc.cursos.msvc_cursos.services;

import com.mahecha.msvc.cursos.msvc_cursos.models.Course;
import com.mahecha.msvc.cursos.msvc_cursos.models.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findByAllCourse();
    Optional<Course> findCourseById(Long id);
    Course saveCourse(Course course);
    void deleteCourse(Long id);

    void deleteCourseUserById(Long id);
    Optional<User> assignUser(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> deleteUser(User user, Long courseId);

    Optional<Course> findCourseUsersById(Long id);
}
