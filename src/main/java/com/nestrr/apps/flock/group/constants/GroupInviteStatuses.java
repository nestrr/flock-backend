package com.nestrr.apps.flock.group.constants;

public enum GroupInviteStatuses {
  ACCEPTED("accepted"),
  REJECTED("rejected"),
  RESPONSE_PENDING("response_pending"),
  EXPIRED("expired");
  private final String value;

  GroupInviteStatuses(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }
}
