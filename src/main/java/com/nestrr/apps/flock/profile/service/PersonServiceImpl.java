package com.nestrr.apps.flock.profile.service;

import static com.nestrr.apps.flock.util.BeanCopyUtils.copyNonNullProperties;

import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;

  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public void createPersonIfNeeded(String id, String email, String name, String image) {
    Person person = personRepository.findById(id).orElse(null);
    if (person != null) {
      person.setLastLogin(LocalDateTime.now());
    } else {
      person = Person.builder().id(id).email(email).name(name).image(image).build();
    }
    personRepository.save(person);
  }

  @Override
  public Person getPerson(String id) throws NoSuchElementException {
    return personRepository
        .findById(id)
        .orElseThrow(
            () -> new NoSuchElementException(String.format("No such person with id %s", id)));
  }

  @Override
  public void updatePerson(Person personToUpdate) {
    Person person = personRepository.findById(personToUpdate.getId()).orElseThrow();
    copyNonNullProperties(personToUpdate, person);

    personRepository.save(person);
  }

  public void deletePerson(String id) {
    personRepository.deleteById(id);
  }
}
