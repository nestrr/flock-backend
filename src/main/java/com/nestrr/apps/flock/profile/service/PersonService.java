package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.entity.Person;

import java.sql.SQLDataException;
import java.util.List;

public interface PersonService {
  Person getOrCreatePerson(String id, String email, String name, String image, List<String> roles);

  void updatePerson(String id, String name, String profilePicture, String bio);

  void deletePerson(String id);
}
