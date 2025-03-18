package com.nestrr.apps.flock.group.controller;

import com.nestrr.apps.flock.group.dto.GroupInviteDto;
import com.nestrr.apps.flock.group.dto.GroupMembershipDto;
import com.nestrr.apps.flock.group.service.GroupFacadeService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-member")
public class GroupMembershipController {
  private final GroupFacadeService groupFacadeService;

  public GroupMembershipController(GroupFacadeService groupFacadeService) {
    this.groupFacadeService = groupFacadeService;
  }

  @GetMapping("/{groupId}")
  @PreAuthorize("@groupServiceImpl.isGroupAdmin(#groupId, authentication.name)")
  public ResponseEntity<List<GroupMembershipDto>> getMembers(@PathVariable String groupId) {
    try {
      return ResponseEntity.ok(groupFacadeService.getMembers(groupId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
