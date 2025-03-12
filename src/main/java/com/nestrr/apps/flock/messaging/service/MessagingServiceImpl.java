package com.nestrr.apps.flock.messaging.service;

import com.nestrr.apps.flock.messaging.dto.AdminChangeContext;
import com.nestrr.apps.flock.messaging.dto.GroupInviteContext;
import com.nestrr.apps.flock.messaging.entity.Email;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

@Service
public class MessagingServiceImpl implements MessagingService {
  private final ISpringTemplateEngine emailTemplateEngine;
  private final EmailSender emailService;
  private final PersonRepository personRepository;

  @Value("${frontend.url}")
  private String frontend;

  public MessagingServiceImpl(
      EmailSender emailService,
      PersonRepository personRepository,
      ISpringTemplateEngine emailTemplateEngine) {
    this.emailService = emailService;
    this.personRepository = personRepository;
    this.emailTemplateEngine = emailTemplateEngine;
  }

  @Override
  public void sendInviteNotification(GroupInviteContext context, List<String> recipientIds) {
    recipientIds.forEach(
        id -> {
          String emailAddress =
              personRepository
                  .findById(id)
                  .map(Person::getEmail)
                  .orElseThrow(
                      () ->
                          new NullPointerException(
                              String.format("Person with ID %s does not have an email", id)));
          Email email = createInviteEmail(context, id, emailAddress);
          emailService.sendSystemEmail(email);
        });
  }

  @Override
  public void sendAdminChangeNotification(AdminChangeContext context) {
    Email removalEmail = createAdminRemovalEmail(context, context.oldAdmin().getEmail());
    Email assignmentEmail = createAdminAssignmentEmail(context, context.newAdmin().getEmail());
    emailService.sendSystemEmail(removalEmail);
    emailService.sendSystemEmail(assignmentEmail);
  }

  public Email createInviteEmail(
      GroupInviteContext context, String recipientId, String recipientEmail) {
    String subject =
        String.format("You're invited to a new group on Nestrr: %s!", context.group().getName());
    Context ctx = new Context(Locale.US);
    ctx.setVariable("pageTitle", subject);
    ctx.setVariable("groupName", context.group().getName());
    ctx.setVariable("groupId", context.group().getId());
    ctx.setVariable("adminId", context.admin().getId());
    ctx.setVariable("adminName", context.admin().getName());
    ctx.setVariable(
        "inviteUrl",
        String.format("%s/invite/%s?id=%s", frontend, context.group().getId(), recipientId));

    String content = emailTemplateEngine.process("group-invite/template.html", ctx);
    return Email.builder()
        .subject(subject)
        .htmlBody(content)
        .recipients(List.of(recipientEmail))
        .build();
  }

  public Email createAdminRemovalEmail(AdminChangeContext context, String recipientEmail) {
    String subject =
        String.format("Important: You're no longer admin of %s!", context.group().getName());
    Context ctx = new Context(Locale.US);
    ctx.setVariable("pageTitle", subject);
    ctx.setVariable("groupName", context.group().getName());
    ctx.setVariable("groupUrl", String.format("%s/invite/%s", frontend, context.group().getId()));
    ctx.setVariable("newAdminName", context.newAdmin().getName());
    ctx.setVariable("newAdminId", context.newAdmin().getId());

    String content = emailTemplateEngine.process("admin-removal/template.html", ctx);
    return Email.builder()
        .subject(subject)
        .htmlBody(content)
        .recipients(List.of(recipientEmail))
        .build();
  }

  public Email createAdminAssignmentEmail(AdminChangeContext context, String recipientEmail) {
    String subject =
        String.format("Important: You're now an admin of %s!", context.group().getName());
    Context ctx = new Context(Locale.US);
    ctx.setVariable("pageTitle", subject);
    ctx.setVariable("groupName", context.group().getName());
    ctx.setVariable("groupUrl", String.format("%s/invite/%s", frontend, context.group().getId()));
    ctx.setVariable("oldAdminId", context.oldAdmin().getId());
    ctx.setVariable("oldAdminName", context.oldAdmin().getName());

    String content = emailTemplateEngine.process("admin-assignment/template.html", ctx);
    return Email.builder()
        .subject(subject)
        .htmlBody(content)
        .recipients(List.of(recipientEmail))
        .build();
  }
}
