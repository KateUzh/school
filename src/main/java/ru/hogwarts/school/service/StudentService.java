package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student editStudent(Long id, Student student) {
        logger.info("Was invoked method for edit student");
        if (!studentRepository.existsById(id)) {
            logger.error("There is not student for edit with id = {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден");
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    public Collection<Student> getStudent() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Optional<Student> findStudent(long id) {
        logger.info("Was invoked method for find student");
        if (!studentRepository.existsById(id)) {
            logger.error("There is not student with id = {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Студент не найден");
        }
        return studentRepository.findById(id);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentsByAge(int age) {
        logger.info("Was invoked method for find students by age");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for find students by age between");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Integer getTotalNumberOfStudents() {
        logger.info("Was invoked method for get total number of students");
        return studentRepository.getTotalNumberOfStudents();
    }

    public Integer getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    public Collection<Student> getFiveLastStudent() {
        logger.info("Was invoked method for get five last students");
        return studentRepository.getFiveLastStudent();
    }
}
