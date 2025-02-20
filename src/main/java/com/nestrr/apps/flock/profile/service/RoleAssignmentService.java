package com.nestrr.apps.flock.profile.service;

import java.util.List;

public interface RoleAssignmentService {
  List<String> getRoles(String personId);

  void storeRoleAssignments(String personId, List<String> roles);

  //  void updateRoleAssignments(String personId, List<String> roles);
}
