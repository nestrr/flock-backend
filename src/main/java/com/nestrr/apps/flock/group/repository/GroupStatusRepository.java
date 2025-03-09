package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupStatus;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupStatusRepository
    extends ListPagingAndSortingRepository<GroupStatus, String>,
        ListCrudRepository<GroupStatus, String> {}
