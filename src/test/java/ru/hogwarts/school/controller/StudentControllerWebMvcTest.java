package ru.hogwarts.school.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    @DisplayName("C - создание студента")
    void createStudentTest() throws Exception {
        Student testStudent = new Student("Kate", 35);
        when(studentService.addStudent(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Kate\",\"age\":35}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kate"));
    }

    @Test
    @DisplayName("R - получение студента")
    void getAllStudentByAgeBetweenTest() throws Exception {
        when(studentService.findStudentsByAgeBetween(20, 40)).thenReturn(Arrays.asList(
                new Student("qwe", 23),
                new Student("ewq", 32)
        ));

        mockMvc.perform(get("/student?minAge=20&maxAge=40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("qwe"))
                .andExpect(jsonPath("$[1].age").value(32));
    }

    @Test
    @DisplayName("U - обновление студента")
    void updateStudentTest() throws Exception {
        Student testStudent = new Student("Kate", 35);
        when(studentService.editStudent(any(Long.class), any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(put("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Kate\",\"age\":35}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kate"));
    }

    @Test
    @DisplayName("D - удаление студента")
    void deleteStudentTest() throws Exception {
        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());
    }
}
