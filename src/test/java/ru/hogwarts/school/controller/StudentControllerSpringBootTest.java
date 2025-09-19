package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentControllerSpringBootTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    TestRestTemplate restTemplate;

    private Student student;

    @BeforeEach
    public void setUp() {
        studentRepository.deleteAll();
        student = studentRepository.save(new Student("TestName", 35));
    }

    @Test
    @DisplayName("C - создание студента")
    public void createStudentTest() throws Exception {
        Student request = new Student("Kate", 30);

        ResponseEntity<Student> studentResponseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/student", request, Student.class);
        Student responseBody = studentResponseEntity.getBody();

        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getName(), request.getName());
        assertEquals(responseBody.getAge(), request.getAge());
    }

    @Test
    @DisplayName("R - получение студента")
    public void readStudentTest() throws Exception{
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + student.getId(), Student.class);
        Student responseBody = studentResponseEntity.getBody();

        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(responseBody);
        assertEquals(student.getId(), responseBody.getId());
        assertEquals(student.getName(), responseBody.getName());
        assertEquals(student.getAge(), responseBody.getAge());
    }

    @Test
    @DisplayName("D - удаление студента")
    public void deleteStudentTest() throws Exception{
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/student/" + student.getId(),
                        HttpMethod.DELETE, null, Student.class);

        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(studentResponseEntity);
    }

    @Test
    @DisplayName("U - обновление студента")
    public void updateStudentTest() throws Exception{
        Student updateStudentRequest = new Student("Katherin", student.getAge());
        HttpEntity<Student> entity = new HttpEntity<>(updateStudentRequest);
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/student/" + student.getId(),
                        HttpMethod.PUT,
                        entity,
                        Student.class);
        Student responseBody = studentResponseEntity.getBody();

        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(responseBody);
        assertEquals(student.getId(), responseBody.getId());
        assertEquals(responseBody.getName(), updateStudentRequest.getName());
        assertEquals(responseBody.getAge(), updateStudentRequest.getAge());
    }
}