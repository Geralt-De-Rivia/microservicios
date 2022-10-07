package com.mahecha.msvc.cursos.msvc_cursos.controllers;

import com.mahecha.msvc.cursos.msvc_cursos.models.Course;
import com.mahecha.msvc.cursos.msvc_cursos.models.User;
import com.mahecha.msvc.cursos.msvc_cursos.services.CourseService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        Optional<Course> optionalCourse = courseService.findCourseUsersById(id); //courseService.findCourseById(id);
        if (optionalCourse.isPresent()) {
            return ResponseEntity.ok(optionalCourse.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Course course, BindingResult result) {
        if (result.hasErrors()){
            return validators(result);
        }
        Course courseDB = courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()){
            return validators(result);
        }

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

    @PutMapping("/assignUser/{course_id}")
    public ResponseEntity<?> assignUser(@RequestBody User user, BindingResult result, @PathVariable Long course_id){
        Optional<User> optionalUser;
        try {
            optionalUser = courseService.assignUser(user, course_id);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }

        if (optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
    }

    @PostMapping("/createUser/{course_id}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long course_id){
        Optional<User> optionalUser;
        try {
            optionalUser = courseService.createUser(user, course_id);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }

        if (optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
    }

    @DeleteMapping("/deleteUser/{course_id}")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @PathVariable Long course_id){
        Optional<User> optionalUser;
        try {
            optionalUser = courseService.deleteUser(user, course_id);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }

        if (optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(optionalUser.get());
    }

    @DeleteMapping("/deleteCourseUser/{user_id}")
    public ResponseEntity<?> deleteCourseUserById(@PathVariable Long user_id){
        courseService.deleteCourseUserById(user_id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validators(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
