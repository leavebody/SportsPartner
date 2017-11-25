package com.sportspartner.dao;

import java.util.List;
import com.sportspartner.model.Person;

public interface PersonDao {
//    public List<Person> getAllPersons();
    public Person getPerson(String userId);
    public boolean newPerson(Person person);
    public boolean updatePerson(Person person);
    public boolean deletePerson(String userId);
}