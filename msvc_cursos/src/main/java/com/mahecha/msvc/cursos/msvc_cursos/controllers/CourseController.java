package com.mahecha.msvc.cursos.msvc_cursos.controllers;

import com.mahecha.msvc.cursos.msvc_cursos.models.Course;
import com.mahecha.msvc.cursos.msvc_cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping()
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseService.findByAllCourse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        Optional<Course> optionalCourse = courseService.findCourseById(id);
        if (optionalCourse.isPresent()) {
            return ResponseEntity.ok(optionalCourse.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Course course) {
        Course courseDB = courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Course course, @PathVariable Long id) {
        Optional<Course> optionalCourse =  courseService.findCourseById(id);
        if (optionalCourse.isPresent()){
            Course courseId = optionalCourse.get();
            courseId.setName(course.getName());
            courseId.setDescription(course.getDescription());
            courseId.setDuration(course.getDuration());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(courseId));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Course> optionalCourse =  courseService.findCourseById(id);
        if (optionalCourse.isPresent()) {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
