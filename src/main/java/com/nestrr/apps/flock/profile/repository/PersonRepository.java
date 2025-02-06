package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Person;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Optional;

public interface PersonRepository extends ListPagingAndSortingRepository<Person, String> {
  Optional<Person> findByEmail(String email);
}
