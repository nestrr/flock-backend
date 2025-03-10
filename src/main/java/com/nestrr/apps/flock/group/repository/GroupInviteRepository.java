package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;

public interface GroupInviteRepository
    extends ListPagingAndSortingRepository<GroupInvite, GroupInviteId>,
        ListCrudRepository<GroupInvite, GroupInviteId>,
        JpaRepository<GroupInvite, GroupInviteId> {

  List<GroupInvite> findByIdGroupId(String groupId);
}
