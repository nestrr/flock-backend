package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupInviteView;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupInviteViewRepository
    extends ListPagingAndSortingRepository<GroupInviteView, GroupInviteId>,
        ListCrudRepository<GroupInviteView, GroupInviteId>,
        JpaRepository<GroupInviteView, GroupInviteId> {
  List<GroupInviteView> findByIdGroupId(String groupId);
}
