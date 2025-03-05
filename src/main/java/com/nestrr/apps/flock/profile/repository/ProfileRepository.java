package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Profile;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.Repository;

public interface ProfileRepository
    extends ListPagingAndSortingRepository<Profile, String>, Repository<Profile, String> {

  List<Profile> findAll();

  Optional<Profile> findById(String personId);
}
