package com.mahecha.msvc.cursos.msvc_cursos.services;

import com.mahecha.msvc.cursos.msvc_cursos.clients.UserClientRest;
import com.mahecha.msvc.cursos.msvc_cursos.models.Course;
import com.mahecha.msvc.cursos.msvc_cursos.models.CourseUser;
import com.mahecha.msvc.cursos.msvc_cursos.models.User;
import com.mahecha.msvc.cursos.msvc_cursos.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserClientRest clientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findByAllCourse() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCourseUserById(Long id) {
        courseRepository.deleteCourseUserById(id);
    }

    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()){
            User userMsvc = clientRest.getUser(user.getUser_id());
            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getUser_id());

            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()){
            return Optional.empty();
        }

        User newUserMsvc = clientRest.save(user);

        Course course = optionalCourse.get();
        CourseUser courseUser = new CourseUser();
        courseUser.setUserId(newUserMsvc.getUser_id());

        course.addCourseUser(courseUser);
        courseRepository.save(course);
        return Optional.of(newUserMsvc);
    }

    @Override
    @Transactional
    public Optional<User> deleteUser(User user, Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()){
            User userMsvc = clientRest.getUser(user.getUser_id());
            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getUser_id());

            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseUsersById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()){
            Course course = optionalCourse.get();
            if (!course.getCourseUsers().isEmpty()) {
                List<Long> ids = course.getCourseUsers().stream().map(CourseUser::getUserId).toList();

                List<User> users = clientRest.getUsersCourse(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }
}
