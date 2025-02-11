package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Person;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface PersonRepository
    extends ListPagingAndSortingRepository<Person, String>, CrudRepository<Person, String> {
  Optional<Person> findByEmail(String email);
}
