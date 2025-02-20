package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.entity.Role;
import com.nestrr.apps.flock.profile.entity.RoleAssignment;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleAssignmentServiceImpl implements RoleAssignmentService {
  private final RoleAssignmentRepository roleAssignmentRepository;
  private final RoleRepository roleRepository;

  public RoleAssignmentServiceImpl(
      RoleAssignmentRepository roleAssignmentRepository, RoleRepository roleRepository) {
    this.roleAssignmentRepository = roleAssignmentRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public List<String> getRoles(String personId) {
    Optional<List<String>> roleAssignments = roleAssignmentRepository.listRolesByPersonId(personId);
    return roleAssignments.orElseGet(List::of);
  }

  @Override
  public void storeRoleAssignments(String personId, List<String> roles) {
    for (String roleName : roles) {
      Role role = roleRepository.findByName(roleName).orElseThrow();
      roleAssignmentRepository.save(
          RoleAssignment.builder().personId(personId).roleId(role.getId()).build());
    }
  }
}
