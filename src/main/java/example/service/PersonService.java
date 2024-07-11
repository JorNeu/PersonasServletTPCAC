package example.service;


import example.entity.Person;
import example.repository.PersonRepository;

import java.sql.SQLException;
import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() throws SQLException {
        return personRepository.findAll();
    }

    public void addPerson(Person person) throws SQLException {
        personRepository.addPerson(person);
    }

    public void deletePerson(int id) throws SQLException {
        personRepository.deletePerson(id);
    }
}