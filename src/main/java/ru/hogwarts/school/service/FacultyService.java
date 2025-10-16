package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);


    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        if (!facultyRepository.existsById(id)) {
            logger.error("There is not faculty for edit with id = {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Факультет не найден");
        }
        faculty.setId(id);
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> getFaculty() {
        logger.info("Was invoked method for get faculty");
        return facultyRepository.findAll();
    }

    public Optional<Faculty> findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        if (!facultyRepository.existsById(id)) {
            logger.error("There is not faculty with id = {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Факультет не найден");
        }
        return facultyRepository.findById(id);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        logger.info("Was invoked method for find faculties by color");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findFacultyByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for find faculties by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }
}
