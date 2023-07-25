package ru.mike.crudappjdbc.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mike.crudappjdbc.models.Employee;
import ru.mike.crudappjdbc.models.Project;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDao {

    private final DataSource dataSource;

    @Autowired
    public EmployeeDao(DataSource dataSource){
        this.dataSource = dataSource;
    }




    public void addEmployee(Employee employee) {
        String query = "INSERT INTO Employees (name, birthday, position_id,salary) " +
                "VALUES(?, ?, ?, ?)";


        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setString(1, employee.getName());
            preparedStatement.setDate(2,
                    new Date(employee.getBirthday().getTime()));
            preparedStatement.setLong(3, employee.getPositionId());
            preparedStatement.setBigDecimal(4, employee.getSalary());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }



    public void updateEmployee(Employee updatedEmployee) {
        String query = "UPDATE employees SET name=?, birthday=?, position_id=?, salary=?" +
                " WHERE id=?";

        try (Connection connection = dataSource.getConnection()) {

            connection.setAutoCommit(true);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, updatedEmployee.getName());
                preparedStatement.setDate(2, new Date(updatedEmployee.getBirthday().getTime()));
                preparedStatement.setLong(3, updatedEmployee.getPositionId());
                preparedStatement.setBigDecimal(4, updatedEmployee.getSalary());
                preparedStatement.setLong(5, updatedEmployee.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

   public void deleteEmployee(long employeeId){
        String query = "DELETE FROM Employees WHERE id=?";
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setLong(1, employeeId);

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
   }

   public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employees";
        try(Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)){
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setBirthday(resultSet.getDate("birthday"));
                employee.setPositionId(resultSet.getLong("position_id"));
                employee.setSalary(resultSet.getBigDecimal("salary"));

                employees.add(employee);

            }


        } catch (SQLException e){
            e.printStackTrace();
        }

        return employees;
   }

   public Employee getEmployeeById(long employeeId){
        String query = "SELECT * FROM Employees WHERE id = ?";

        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);){

            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setBirthday(resultSet.getDate("birthday"));
                employee.setSalary(resultSet.getBigDecimal("salary"));

                System.out.println(employee);

                return employee;

            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
   }

   public Employee getEmployeeByName(String employeeName) {
        String query = "SELECT * FROM Employees WHERE name = ?";

        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setString(1, employeeName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setBirthday(resultSet.getDate("birthday"));
                employee.setSalary(resultSet.getBigDecimal("salary"));

                return employee;
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
   }

   public List<Project> getProjectsThisEmployee(long employeeId) {
       List<Project> projects = new ArrayList<>();
       String query = "SELECT p.* FROM Projects p JOIN employee_project ep ON " +
               "p.id = ep.project_id WHERE ep.employee_id = ? ";

       try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ) {

           preparedStatement.setLong(1, employeeId);

           ResultSet resultSet = preparedStatement.executeQuery();


           while(resultSet.next()){
               Project project = new Project();
               project.setId(resultSet.getLong("id"));
               project.setName(resultSet.getString("name"));
               project.setFounded(resultSet.getDate("founded"));

               projects.add(project);
           }

       } catch (SQLException e){
           e.printStackTrace();
       }

       return projects;
   }

   public List<Employee> getEmployeeByPosition(String positionName){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.* FROM Employees e JOIN Positions p ON " +
                "e.position_id = p.id WHERE p.name = ?";

        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, positionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setBirthday(resultSet.getDate("birthday"));
                employee.setSalary(resultSet.getBigDecimal("salary"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
   }

    public List<Project> getProjectsByEmployeeName(String employeeName) {
        String query = "SELECT p.* FROM projects p " +
                "INNER JOIN employee_project ep ON p.id = ep.project_id " +
                "INNER JOIN employees e ON ep.employee_id = e.id " +
                "WHERE e.name = ?";



        List<Project> projects = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employeeName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong("id"));
                project.setName(resultSet.getString("name"));


                projects.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }




    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}
