package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    Map<Long, Faculty> facultyMap = new HashMap<>();
    long count = 0;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(count++);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        if (!facultyMap.containsKey(id)) {
            return null;
        } facultyMap.put(id, faculty);
        faculty.setId(id);
        return faculty;
    }

    public Map<Long, Faculty> getFaculty() {
        return facultyMap;
    }

    public Faculty findFaculty(Long id) {
        return facultyMap.get(id);
    }

    public Faculty deleteFaculty(Long id) {
        return facultyMap.remove(id);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultyMap.values()) {
            if (faculty.getColor().equalsIgnoreCase(color)) {
                result.add(faculty);
            }
        }
        return result;
    }
}
