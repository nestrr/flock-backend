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
    return ResponseEntity.ok(groupFacadeService.getSelfGroups(auth));
  }

  @PostMapping
  public ResponseEntity<String> saveGroup(
      Authentication auth, @Valid @RequestBody NewGroupRequest newGroupRequest) {
    groupFacadeService.createGroup(auth, newGroupRequest);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}")
  @PreAuthorize("isGroupOwner(#groupId, authentication.name)")
  public ResponseEntity<String> updateGroup(
      @PathVariable String groupId, @Valid @RequestBody UpdateGroupRequest updateGroupRequest) {
    groupFacadeService.updateGroup(updateGroupRequest);
    return ResponseEntity.ok().build();
  }

  public boolean isGroupOwner(String groupId, String personId) {
    return groupFacadeService.isGroupOwner(groupId, personId);
  }
}
