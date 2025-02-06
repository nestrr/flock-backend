package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Role;

import java.util.Optional;

import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface RoleRepository extends ListPagingAndSortingRepository<Role, String> {
  Optional<Role> findByName(String name);
}
