package com.sportspartner.dao;

import java.sql.SQLException;
import java.util.List;
import com.sportspartner.model.Person;

public interface PersonDao {
//    public List<Person> getAllPersons() throws SQLException;
    public Person getPerson(String userId) throws SQLException;
    public boolean newPerson(Person person) throws SQLException;
    public boolean updatePerson(Person person) throws SQLException;
    public boolean deletePerson(String userId) throws SQLException;
}