package com.nestrr.apps.flock.messaging.service;

import com.nestrr.apps.flock.messaging.entity.Email;
import com.nestrr.apps.flock.messaging.repository.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderImpl implements EmailSender {

  private final JavaMailSender mailSender;
  private final EmailRepository emailRepository;

  @Value("${spring.mail.username}")
  private String systemEmailAddress;

  public EmailSenderImpl(JavaMailSender mailSender, EmailRepository emailRepository) {
    this.mailSender = mailSender;
    this.emailRepository = emailRepository;
  }

  @Override
  public void sendSystemEmail(Email email) {
    MimeMessage message = this.mailSender.createMimeMessage();
    try {
      MimeMessageHelper messageHelper =
          new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");

      messageHelper.setFrom(systemEmailAddress);
      messageHelper.setTo(email.getRecipients().toArray(new String[] {}));
      messageHelper.setSubject(email.getSubject());
      messageHelper.setText(email.getTextBody(), email.getHtmlBody());
      this.mailSender.send(message);
    } catch (Exception e) {
      email.setSuccess(false);
      emailRepository.save(email);
      throw new MailSendException(
          String.format("System email could not be sent to %s.", email.getRecipients().toString()));
    }
    email.setSuccess(true);
    emailRepository.save(email);
  }
}
