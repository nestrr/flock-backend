package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.entity.Person;
import java.util.NoSuchElementException;

public interface PersonService {
  void createPersonIfNeeded(String id, String email, String name, String image);

  Person getPerson(String id) throws NoSuchElementException;

  void updatePerson(Person person);

  void deletePerson(String id);
}
