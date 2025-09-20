package ru.hogwarts.school.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    void createFacultyTest() throws Exception {
        Faculty testFaculty = new Faculty("QWE", "purple");
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(testFaculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"QWE\",\"color\":\"purple\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("QWE"));
    }

    @Test
    void getFacultyByNameOrColorTest() throws Exception {
        when(facultyService.findFacultyByNameOrColor("purple")).thenReturn(Arrays.asList(
                new Faculty("EWQ", "purple"),
                new Faculty("Purple", "green")
        ));

        mockMvc.perform(get("/faculty?nameOrColor=purple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("EWQ"))
                .andExpect(jsonPath("$[1].color").value("green"));
    }

    @Test
    void updateFacultyTest() throws Exception {
        Faculty testFaculty = new Faculty("QWE", "red");
        when(facultyService.editFaculty(any(Long.class), any(Faculty.class))).thenReturn(testFaculty);

        mockMvc.perform(put("/faculty/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"QWE\",\"color\":\"red\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("QWE"));
    }

    @Test
    void deleteFacultyTest() throws Exception{
        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());
    }
}
