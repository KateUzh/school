package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long count = 0;

    public Student addStudent(Student student) {
        student.setId(count++);
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student editStudent(long id, Student student) {
        if (!studentMap.containsKey(id)) {
            return null;
        }
        studentMap.put(id, student);
        student.setId(id);
        return student;
    }

    public Map<Long, Student> getStudent() {
        return studentMap;
    }

    public Student findStudent(long id) {
        return studentMap.get(id);
    }

    public Student deleteStudent(long id) {
        return studentMap.remove(id);
    }

    public Collection<Student> findStudentByAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentMap.values()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }

    public Map<Long, Student> getAll() {
        return new HashMap<>(studentMap);
    }
}
