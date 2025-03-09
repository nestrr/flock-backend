package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupInviteStatus;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupInviteStatusRepository
    extends ListPagingAndSortingRepository<GroupInviteStatus, String>,
        ListCrudRepository<GroupInviteStatus, String> {}
