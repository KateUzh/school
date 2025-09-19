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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerSpringBootTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    TestRestTemplate restTemplate;

    private Faculty faculty;

    @BeforeEach
    public void setUp() {
        facultyRepository.deleteAll();
        faculty = facultyRepository.save(new Faculty("TestName", "White"));
    }

    @Test
    @DisplayName("C - создание факультета")
    public void createFacultyTest() throws Exception {
        Faculty request = new Faculty("GRF", "red");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", request, Faculty.class);
        Faculty responseBody = facultyResponseEntity.getBody();

        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getName(), request.getName());
        assertEquals(responseBody.getColor(), request.getColor());
    }

    @Test
    @DisplayName("R - получение факультета")
    public void readFacultyTest() throws Exception{
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);
        Faculty responseBody = facultyResponseEntity.getBody();

        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(responseBody);
        assertEquals(faculty.getId(), responseBody.getId());
        assertEquals(faculty.getName(), responseBody.getName());
        assertEquals(faculty.getColor(), responseBody.getColor());
    }

    @Test
    @DisplayName("D - удаление факультета")
    public void deleteFacultyTest() throws Exception{
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/faculty/" + faculty.getId(),
                        HttpMethod.DELETE, null, Faculty.class);

        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(facultyResponseEntity);
    }

    @Test
    @DisplayName("U - обновление факультета")
    public void updateFacultyTest() throws Exception{
        Faculty updateFacultyRequest = new Faculty("SLZ", faculty.getColor());
        HttpEntity<Faculty> entity = new HttpEntity<>(updateFacultyRequest);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/faculty/" + faculty.getId(),
                        HttpMethod.PUT,
                        entity,
                        Faculty.class);
        Faculty responseBody = facultyResponseEntity.getBody();

        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertNotNull(responseBody);
        assertEquals(faculty.getId(), responseBody.getId());
        assertEquals(responseBody.getName(), updateFacultyRequest.getName());
        assertEquals(responseBody.getColor(), updateFacultyRequest.getColor());
    }
}
