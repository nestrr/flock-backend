package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.GroupMembershipDto;
import com.nestrr.apps.flock.group.entity.GroupMembership;
import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import com.nestrr.apps.flock.group.mapper.GroupMapper;
import com.nestrr.apps.flock.group.mapper.GroupMembershipMapper;
import com.nestrr.apps.flock.group.repository.GroupMembershipRepository;
import com.nestrr.apps.flock.group.repository.GroupMembershipViewRepository;
import com.nestrr.apps.flock.group.repository.GroupViewRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {
  private final GroupMembershipRepository groupMembershipRepository;
  private final GroupMembershipViewRepository groupMembershipViewRepository;
  private final GroupViewRepository groupViewRepository;
  private final GroupMapper groupMapper;
  private final GroupMembershipMapper groupMembershipMapper;

  public GroupMembershipServiceImpl(
      GroupMembershipRepository groupMembershipRepository,
      GroupMembershipViewRepository groupMembershipViewRepository,
      GroupViewRepository groupViewRepository,
      GroupMapper groupMapper,
      GroupMembershipMapper groupMembershipMapper) {
    this.groupMembershipRepository = groupMembershipRepository;
    this.groupMembershipViewRepository = groupMembershipViewRepository;
    this.groupViewRepository = groupViewRepository;
    this.groupMapper = groupMapper;
    this.groupMembershipMapper = groupMembershipMapper;
  }

  @Override
  @Transactional
  public void addMember(String groupId, String personId) {
    groupMembershipRepository.save(
        GroupMembership.builder().id(new GroupMembershipId(groupId, personId)).build());
  }

  @Override
  public boolean isGroupMember(String groupId, String personId) {
    return groupMembershipRepository.findById(new GroupMembershipId(groupId, personId)).isPresent();
  }

  @Override
  @Transactional
  public void removeMember(String groupId, String personId) {
    groupMembershipRepository.deleteById(new GroupMembershipId(groupId, personId));
  }

  @Override
  public List<GroupDto> getGroupsByPersonId(String personId) {
    return groupViewRepository.findGroupsByPersonId(personId).stream()
        .map(groupMapper::toGroupDto)
        .toList();
  }

  @Override
  public List<GroupDto> getGroupsByPersonAndStatusName(String personId, String statusName) {
    return groupViewRepository.findGroupsByPersonAndStatusName(personId, statusName).stream()
        .map(groupMapper::toGroupDto)
        .toList();
  }

  @Override
  public List<GroupMembership> getMembershipsByGroupId(String groupId) {
    return groupMembershipRepository.findByIdGroupId(groupId).stream().toList();
  }

  @Override
  public List<GroupMembershipDto> getMembershipViewsByGroupId(String groupId) {
    return groupMembershipViewRepository.findByIdGroupId(groupId).stream()
        .map(groupMembershipMapper::toGroupMembershipDto)
        .toList();
  }
}
