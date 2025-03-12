package com.nestrr.apps.flock.group.controller;

import com.nestrr.apps.flock.group.service.GroupFacadeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-invite")
public class GroupInviteController {
  private final GroupFacadeService groupFacadeService;

  public GroupInviteController(GroupFacadeService groupFacadeService) {
    this.groupFacadeService = groupFacadeService;
  }

  @PostMapping("/{groupId}/member/{memberId}")
  @PreAuthorize("@groupMembershipServiceImpl.isGroupMember(#groupId, authentication.name)")
  public ResponseEntity<String> createInvite(
      Authentication auth, @PathVariable String groupId, @PathVariable String memberId) {
    try {
      groupFacadeService.inviteUsers(auth, groupId, List.of(memberId));
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/{groupId}/member/{memberId}")
  @PreAuthorize("#memberId == authentication.name")
  public ResponseEntity<String> respondToInvite(
      Authentication auth,
      @PathVariable String groupId,
      @PathVariable String memberId,
      @RequestParam String statusId) {
    try {
      groupFacadeService.respondToInvite(auth, groupId, memberId, statusId);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/{groupId}/member")
  @PreAuthorize("@groupMembershipServiceImpl.isGroupMember(#groupId, authentication.name)")
  public ResponseEntity<String> createInvites(
      Authentication auth,
      @PathVariable String groupId,
      @Valid @RequestBody List<String> memberIds) {
    try {
      groupFacadeService.inviteUsers(auth, groupId, memberIds);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
