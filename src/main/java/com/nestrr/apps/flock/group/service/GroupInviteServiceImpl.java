package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.constants.GroupInviteConstants;
import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.GroupInviteStatus;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import com.nestrr.apps.flock.group.repository.GroupInviteRepository;
import com.nestrr.apps.flock.group.repository.GroupInviteStatusRepository;
import com.nestrr.apps.flock.messaging.dto.GroupInviteContext;
import com.nestrr.apps.flock.messaging.service.MessagingService;
import com.nestrr.apps.flock.profile.entity.Person;
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
  private final PersonRepository personRepository;
  private final MessagingService messagingService;

  public GroupInviteServiceImpl(
      GroupInviteRepository groupInviteRepository,
      GroupInviteStatusRepository groupInviteStatusRepository,
      MessagingService messagingService,
      PersonRepository personRepository) {
    this.groupInviteRepository = groupInviteRepository;
    this.groupInviteStatusRepository = groupInviteStatusRepository;
    this.messagingService = messagingService;
    this.personRepository = personRepository;
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

    sendInvites(group, recipientIds);
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

  private void sendInvites(Group group, List<String> recipientIds) {
    Person admin =
        personRepository
            .findById(group.getAdminId())
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format(
                            "No Person object found for admin ID %s (group ID %s)",
                            group.getAdminId(), group.getId())));

    GroupInviteContext context = GroupInviteContext.builder().group(group).admin(admin).build();
    messagingService.sendInviteNotification(context, recipientIds);
  }
}
