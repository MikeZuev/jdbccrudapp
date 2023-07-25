package ru.mike.crudappjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mike.crudappjdbc.models.Position;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PositionDao {
    private final DataSource dataSource;

    @Autowired
    public PositionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addPosition(Position position) {
        String query = "INSERT INTO positions (name) VALUES (?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, position.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePosition(Position updatedPosition) {
        String query = "UPDATE positions SET name = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, updatedPosition.getName());
            preparedStatement.setLong(2, updatedPosition.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePosition(long positionId) {
        String query = "DELETE FROM positions WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setLong(1, positionId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<>();
        String query = "SELECT * FROM positions";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Position position = new Position();
                position.setId(resultSet.getLong("id"));
                position.setName(resultSet.getString("name"));
                positions.add(position);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return positions;
    }

    public Position getPositionById(long positionId) {
        String query = "SELECT * FROM positions WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setLong(1, positionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Position position = new Position();
                position.setId(resultSet.getLong("id"));
                position.setName(resultSet.getString("name"));
                return position;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
