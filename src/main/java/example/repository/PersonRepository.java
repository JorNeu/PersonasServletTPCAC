package example.repository;

import example.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private Connection connection;

    public PersonRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Person> findAll() throws SQLException {
        List<Person> people = new ArrayList<>();
        String query = "SELECT * FROM persona";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setNombre(rs.getString("nombre"));
                person.setAnios(rs.getInt("anios"));
                people.add(person);
            }
        }
        return people;
    }

    public void addPerson(Person person) throws SQLException {
        String query = "INSERT INTO persona (nombre, anios) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, person.getNombre());
            stmt.setInt(2, person.getAnios());
            stmt.executeUpdate();
        }
    }

    public void deletePerson(int id) throws SQLException {
        String query = "DELETE FROM persona WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}