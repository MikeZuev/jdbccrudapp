package ru.mike.crudappjdbc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mike.crudappjdbc.dao.EmployeeDao;
import ru.mike.crudappjdbc.models.Employee;
import ru.mike.crudappjdbc.models.Project;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeController(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }

    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            employeeDao.addEmployee(employee);
            return ResponseEntity.status((HttpStatus.CREATED))
                    .body("Employee was added.");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error happened while trying to add employee.");
        }
    }

   @PutMapping("/{id}")
   public ResponseEntity<String> updateEmployee(@PathVariable long id,
                                                @RequestBody Employee updatedEmployee){
        try {
            Employee existingEmployee= employeeDao.getEmployeeById(id);
            if(existingEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Employee not found");
            }

            updatedEmployee.setId(id);
            employeeDao.updateEmployee(updatedEmployee);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Employee wsa updated");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during updating employee");
        }
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEployee(@PathVariable long id) {
       try {
           Employee existingExmployee = employeeDao.getEmployeeById(id);
           if (existingExmployee == null) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("Employee not found.");

           }

           employeeDao.deleteEmployee(id);
           return ResponseEntity.status(HttpStatus.OK)
                   .body("Employee was deleted");


       } catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error occurred during deleting employee");
       }
   }

   @GetMapping(produces = "application/json")
   @ResponseBody
   @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeDao.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
   }

   @GetMapping("/{id}")
   public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        try{
            Employee employee = employeeDao.getEmployeeById(id);
            if(employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
            return ResponseEntity.ok(employee);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
   }

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> getEmployeeByName(@PathVariable String name) {
        try {
            Employee employee = employeeDao.getEmployeeByName(name);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Employee not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while getting  employee by name.");
        }
    }

    @GetMapping("/byname/{name}/projects")
    public ResponseEntity<?> getProjectsByEmployeeName(@PathVariable String name) {
        try {
            List<Project> projects = employeeDao.getProjectsByEmployeeName(name);
            if (!projects.isEmpty()) {
                return ResponseEntity.ok(projects);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No projects found for the employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while getting  projects of employee.");
        }
    }

    @GetMapping("/byposition/{positionName}")
    public ResponseEntity<List<Employee>> getEmployeeByPosition(@PathVariable
                                                                String positionName){
        try {
            List<Employee> employees =
                    employeeDao.getEmployeeByPosition(positionName);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }



}
