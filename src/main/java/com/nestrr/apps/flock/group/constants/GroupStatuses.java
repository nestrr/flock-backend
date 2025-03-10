package com.nestrr.apps.flock.group.constants;

public enum GroupStatuses {
  ACTIVE("active"),
  PENDING("pending");
  public final String value;

  GroupStatuses(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }
}
