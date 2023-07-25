package ru.mike.crudappjdbc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mike.crudappjdbc.dao.ProjectDao;
import ru.mike.crudappjdbc.models.Employee;
import ru.mike.crudappjdbc.models.Project;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectDao projectDao;

    @Autowired
    public ProjectController(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @PostMapping
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        try {
            projectDao.addProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body("Project added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding project.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProject(@PathVariable long id, @RequestBody Project updatedProject) {
        try {
            Project existingProject = projectDao.getProjectById(id);
            if (existingProject == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
            }

            updatedProject.setId(id);
            projectDao.updateProject(updatedProject);
            return ResponseEntity.status(HttpStatus.OK).body("Project updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating project.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable long id) {
        try {
            Project existingProject = projectDao.getProjectById(id);
            if (existingProject == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
            }

            projectDao.deleteProject(id);
            return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting project.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projects = projectDao.getAllProjects();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable long id) {
        try {
            Project project = projectDao.getProjectById(id);
            if (project == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{projectName}/employees")
    public ResponseEntity<?> getEmployeesByProjectName(@PathVariable String projectName) {
        try {
            List<Employee> employees = projectDao.getEmployeesByProjectName(projectName);
            if (!employees.isEmpty()) {
                return ResponseEntity.ok(employees);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No employees found for the project.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while getting employees for the project.");
        }
    }
}
