package com.nestrr.apps.flock.profile.service;

import static com.nestrr.apps.flock.util.BeanCopyUtils.copyNonNullProperties;

import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import java.sql.SQLDataException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;

  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public void createPersonIfNeeded(String id, String email, String name, String image) {
    Optional<Person> existingPerson = personRepository.findById(id);
    existingPerson.orElseGet(
        () ->
            personRepository.save(
                Person.builder().id(id).email(email).name(name).image(image).build()));
  }

  @Override
  public Person getPerson(String id) throws SQLDataException {
    return personRepository.findById(id).orElseThrow(() -> new SQLDataException("No such person!"));
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
