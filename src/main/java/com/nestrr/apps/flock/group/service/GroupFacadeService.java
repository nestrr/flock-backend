package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import org.springframework.security.core.Authentication;

public interface GroupFacadeService {
  void createGroup(Authentication auth, NewGroupRequest newGroupRequest);
}
