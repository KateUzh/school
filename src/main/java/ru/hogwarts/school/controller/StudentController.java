package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id).get();
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("{id}")
    public Student editStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.editStudent(id, student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentsAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        if (minAge > 0 && maxAge > minAge) {
            return ResponseEntity.ok(studentService.findStudentsByAgeBetween(minAge, maxAge));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Student student = studentService.findStudent(id).get();
        return ResponseEntity.ok(student.getFaculty());
    }

    @GetMapping("/get-total-number")
    public Integer getTotalNumberOfStudents() {
        return studentService.getTotalNumberOfStudents();
    }

    @GetMapping("/get-average-age")
    public Integer getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/get-five-last-students")
    public Collection<Student> getFiveLastStudents() {
        return studentService.getFiveLastStudent();
    }

    @GetMapping("/get-students_whose_name_started_with_A")
    public List<String> getStudentsWhoseNameStartedWithA() {
        return studentService.getStudentsWhoseNameStartedWithA();
    }

    @GetMapping("/get-average-age-with-stream")
    public Double getAverageAgeOfStudentsWithStream() {
        return studentService.getAverageAgeOfStudentsWithStream();
    }

    @GetMapping("/print-parallel")
    public void printStudentsNamesParallel() {
        studentService.printStudentsNamesParallel();
    }

    @GetMapping("/print-synchronized")
    public void printStudentsNamesSynchronized() {
        studentService.printStudentsNamesSynchronized(0);
        studentService.printStudentsNamesSynchronized(1);

        new Thread(() -> {
            studentService.printStudentsNamesSynchronized(2);
            studentService.printStudentsNamesSynchronized(3);
        }).start();

        new Thread(() -> {
            studentService.printStudentsNamesSynchronized(4);
            studentService.printStudentsNamesSynchronized(5);
        }).start();
    }
}
