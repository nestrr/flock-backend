package com.nestrr.apps.flock.group.controller;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.service.GroupFacadeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

  private final GroupFacadeService groupFacadeService;

  public GroupController(GroupFacadeService groupFacadeService) {
    this.groupFacadeService = groupFacadeService;
  }

  @GetMapping("/me")
  public ResponseEntity<List<GroupDto>> getSelfGroups(Authentication auth) {
    var x = ResponseEntity.ok(groupFacadeService.getSelfGroups(auth));
    return x;
  }

  @PostMapping
  public ResponseEntity<String> saveGroup(
      Authentication auth, @Valid @RequestBody NewGroupRequest newGroupRequest) {
    groupFacadeService.createGroup(auth, newGroupRequest);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{groupId}")
  @PreAuthorize("@groupFacadeServiceImpl.isGroupOwner(#groupId, authentication.name)")
  public ResponseEntity<String> updateGroup(
      @PathVariable String groupId, @Valid @RequestBody UpdateGroupRequest updateGroupRequest) {
    groupFacadeService.updateGroup(groupId, updateGroupRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{groupId}")
  @PreAuthorize("@groupServiceImpl.isGroupAdmin(#groupId, authentication.name)")
  public ResponseEntity<String> deleteGroup(@PathVariable String groupId) {
    groupFacadeService.deleteGroup(groupId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{groupId}/{memberId}")
  @PreAuthorize(
      "#memberId == authentication.name || @groupFacadeServiceImpl.isGroupOwner(#groupId, authentication.name)")
  public ResponseEntity<String> removeMember(
      Authentication auth, @PathVariable String groupId, @PathVariable String memberId) {
    try {
      groupFacadeService.removeMember(auth, groupId, memberId);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest()
          .body("An admin cannot leave the group. Assign another admin first.");
    }
  }
}
