package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.constants.GroupInviteConstants;
import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.GroupInviteStatus;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import com.nestrr.apps.flock.group.repository.GroupInviteRepository;
import com.nestrr.apps.flock.group.repository.GroupInviteStatusRepository;
import com.nestrr.apps.flock.messaging.service.MessagingService;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
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
  public void createInvites(Group group, List<String> recipientIds) {
    String pendingStatusId = getStatusId(GroupInviteStatuses.RESPONSE_PENDING);
    String acceptedStatusId = getStatusId(GroupInviteStatuses.ACCEPTED);
    recipientIds.forEach(
        recipientId -> {
          GroupInvite invite =
              groupInviteRepository
                  .findById(new GroupInviteId(group.getId(), recipientId))
                  .orElse(null);
          if (invite == null || !invite.getStatusId().equals(acceptedStatusId)) {
            storeInvite(group.getId(), recipientId, pendingStatusId);
          }
        });
  }

  @Override
  public List<GroupInvite> getGroupInvites(String groupId, GroupInviteStatuses status) {
    List<GroupInvite> invites = groupInviteRepository.findByIdGroupId(groupId);
    GroupInviteStatus completeStatus =
        groupInviteStatusRepository
            .findByName(status.value())
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format(
                            "Group invite status with value %s does not exist", status.value())));
    return invites.stream().filter((i) -> i.getStatusId().equals(completeStatus.getId())).toList();
  }

  @Override
  public void deleteByGroupId(String groupId) {
    groupInviteRepository.deleteByIdGroupId(groupId);
  }

  @Override
  public GroupInviteStatuses acceptInvite(String groupId, String memberId, String statusId) {
    GroupInvite existingInvite =
        groupInviteRepository
            .findById(new GroupInviteId(groupId, memberId))
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format(
                            "No invite found for group ID %s and member ID %s",
                            groupId, memberId)));
    if (existingInvite.getExpiresOn().isBefore(LocalDateTime.now()))
      throw new IllegalArgumentException(
          String.format("Invite for group ID %s and member ID %s has expired", groupId, memberId));
    existingInvite.setStatusId(statusId);
    groupInviteRepository.save(existingInvite);
    return getStatus(statusId);
  }

  private void storeInvite(String groupId, String recipientId, String statusId) {
    groupInviteRepository.save(
        GroupInvite.builder()
            .id(new GroupInviteId(groupId, recipientId))
            .statusId(statusId)
            .expiresOn(LocalDateTime.now().plusDays(GroupInviteConstants.INVITE_WAIT_PERIOD_DAYS))
            .build());
  }

  private String getStatusId(GroupInviteStatuses status) {
    return groupInviteStatusRepository
        .findByName(status.value())
        .map(GroupInviteStatus::getId)
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    String.format(
                        "No appropriate group invite status ID found for status: %s.",
                        status.value())));
  }

  private GroupInviteStatuses getStatus(String id) {
    String name =
        groupInviteStatusRepository
            .findById(id)
            .map(GroupInviteStatus::getName)
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format("No status found for status ID: %s.", id)));
    return GroupInviteStatuses.valueOf(name);
  }
}
