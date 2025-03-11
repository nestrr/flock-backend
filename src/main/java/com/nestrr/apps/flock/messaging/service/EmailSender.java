package com.nestrr.apps.flock.messaging.service;

import com.nestrr.apps.flock.messaging.entity.Email;

public interface EmailSender {

  void sendSystemEmail(Email email);
}
