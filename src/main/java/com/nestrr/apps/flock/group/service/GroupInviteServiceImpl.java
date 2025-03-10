package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.constants.GroupInviteConstants;
import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.dto.NewGroupInviteRequest;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.GroupInviteStatus;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import com.nestrr.apps.flock.group.repository.GroupInviteRepository;
import com.nestrr.apps.flock.group.repository.GroupInviteStatusRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupInviteServiceImpl implements GroupInviteService {
  private final GroupInviteRepository groupInviteRepository;
  private final GroupInviteStatusRepository groupInviteStatusRepository;

  public GroupInviteServiceImpl(
      GroupInviteRepository groupInviteRepository,
      GroupInviteStatusRepository groupInviteStatusRepository) {
    this.groupInviteRepository = groupInviteRepository;
    this.groupInviteStatusRepository = groupInviteStatusRepository;
  }

  @Transactional
  @Override
  public void createInvites(NewGroupInviteRequest request) {
    this.storeInvite(request.groupId(), request.recipientId(), getPendingStatusId());
  }

  @Transactional
  @Override
  public void createInvites(String groupId, List<String> recipientIds) {
    recipientIds.forEach(id -> storeInvite(groupId, id, getPendingStatusId()));
  }

  private void storeInvite(String groupId, String recipientId, String statusId) {
    groupInviteRepository.save(
        GroupInvite.builder()
            .id(new GroupInviteId(groupId, recipientId))
            .statusId(statusId)
            .expiresOn(LocalDateTime.now().plusDays(GroupInviteConstants.INVITE_WAIT_PERIOD_DAYS))
            .build());
  }

  private String getPendingStatusId() {
    String pending = GroupInviteStatuses.RESPONSE_PENDING.value();

    return groupInviteStatusRepository
        .findByName(pending)
        .map(GroupInviteStatus::getId)
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    String.format(
                        "No appropriate group invite status ID found for status: %s.", pending)));
  }
}
