package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.entity.Role;
import com.nestrr.apps.flock.profile.entity.RoleAssignment;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nestrr.apps.flock.util.BeanCopyUtils.copyNonNullProperties;

@Service
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;
  private final RoleAssignmentRepository roleAssignmentRepository;
  private final RoleRepository roleRepository;

  public PersonServiceImpl(
      PersonRepository personRepository,
      RoleAssignmentRepository roleAssignmentRepository,
      RoleRepository roleRepository) {
    this.personRepository = personRepository;
    this.roleAssignmentRepository = roleAssignmentRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public Person getOrCreatePerson(
      String id, String email, String name, String image, List<String> roles) {
    Optional<Person> existingPerson = personRepository.findById(id);
    return existingPerson.orElseGet(
        () ->
            createPerson(
                Person.builder().id(id).email(email).name(name).image(image).build(), roles));
  }

  public Person createPerson(Person toCreate, List<String> roles) {
    Person person = personRepository.save(toCreate);
    for (String role : roles) {
      assignRole(person.getId(), role);
    }
    return person;
  }

  public void assignRole(String personId, String roleName) {
    Role role = roleRepository.findByName(roleName).orElseThrow();
    roleAssignmentRepository.save(
        RoleAssignment.builder().personId(personId).roleId(role.getId()).build());
  }

  @Override
  public void updatePerson(String id, String name, String profilePicture, String bio) {
    Person person = personRepository.findById(id).orElseThrow();
    copyNonNullProperties(
        Person.builder().name(name).bio(bio).image(profilePicture).build(), person);

    personRepository.save(person);
  }

  public void deletePerson(String id) {
    personRepository.deleteById(id);
  }
}
