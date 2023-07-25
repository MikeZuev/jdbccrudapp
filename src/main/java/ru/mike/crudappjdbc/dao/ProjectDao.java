package ru.mike.crudappjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mike.crudappjdbc.models.Employee;
import ru.mike.crudappjdbc.models.Project;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDao {
    private final DataSource dataSource;

    @Autowired
    public ProjectDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addProject(Project project) {
        String query = "INSERT INTO projects (name, founded) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.getName());
            preparedStatement.setDate(2, new Date(project.getFounded().getTime()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProject(Project updatedProject) {
        String query = "UPDATE projects SET name = ?, founded = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, updatedProject.getName());
            preparedStatement.setDate(2, new Date(updatedProject.getFounded().getTime()));
            preparedStatement.setLong(3, updatedProject.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(long projectId) {
        String query = "DELETE FROM projects WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, projectId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong("id"));
                project.setName(resultSet.getString("name"));
                project.setFounded(resultSet.getDate("founded"));
                projects.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public Project getProjectById(long projectId) {
        String query = "SELECT * FROM projects WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong("id"));
                project.setName(resultSet.getString("name"));
                project.setFounded(resultSet.getDate("founded"));
                return project;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Employee> getEmployeesByProjectName(String projectName) {
        String query = "SELECT e.* FROM employees e " +
                "INNER JOIN employee_project ep ON e.id = ep.employee_id " +
                "INNER JOIN projects p ON ep.project_id = p.id " +
                "WHERE p.name = ?";

        List<Employee> employees = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, projectName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                // Set other properties of the Employee object as needed

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
}
