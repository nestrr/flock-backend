package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupInviteRepository
    extends ListPagingAndSortingRepository<GroupInvite, GroupInviteId>,
        ListCrudRepository<GroupInvite, GroupInviteId> {}
