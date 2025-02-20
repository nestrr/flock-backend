package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.entity.Person;
import java.sql.SQLDataException;

public interface PersonService {
  void createPersonIfNeeded(String id, String email, String name, String image);

  Person getPerson(String id) throws SQLDataException;

  void updatePerson(Person person);

  void deletePerson(String id);
}
