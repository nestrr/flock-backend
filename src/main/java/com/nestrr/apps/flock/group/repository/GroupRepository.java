package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.Group;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupRepository
    extends ListPagingAndSortingRepository<Group, String>, ListCrudRepository<Group, String> {}
