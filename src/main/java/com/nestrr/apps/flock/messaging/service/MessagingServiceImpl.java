package com.nestrr.apps.flock.messaging.service;

import com.nestrr.apps.flock.messaging.dto.*;
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

  @Override
  public void sendGroupDeleteNotification(GroupDeleteContext context) {
    List<String> memberEmails =
        context.memberIds().stream()
            .map(
                id ->
                    personRepository
                        .findById(id)
                        .map(Person::getEmail)
                        .orElseThrow(
                            () ->
                                new NullPointerException(
                                    String.format("Person with ID %s does not have an email", id))))
            .toList();
    memberEmails.forEach(
        email -> {
          emailService.sendSystemEmail(createGroupDeletionEmail(context, email));
        });
  }

  @Override
  public void sendGroupMemberWelcomeNotification(NewGroupMembershipContext context) {
    Email email = createWelcomeEmail(context, context.member().getEmail());
    emailService.sendSystemEmail(email);
  }

  @Override
  public void sendGroupMemberGoodbyeNotification(DeletedGroupMembershipContext context) {}

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
    return Email.builder().subject(subject).htmlBody(content).recipient(recipientEmail).build();
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

    String content = emailTemplateEngine.process("group-admin-removal/template.html", ctx);
    return Email.builder().subject(subject).htmlBody(content).recipient(recipientEmail).build();
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

    String content = emailTemplateEngine.process("group-admin-assignment/template.html", ctx);
    return Email.builder().subject(subject).htmlBody(content).recipient(recipientEmail).build();
  }

  public Email createGroupDeletionEmail(GroupDeleteContext context, String recipientEmail) {
    String subject = String.format("Important: %s has shut down.", context.group().getName());
    Context ctx = new Context(Locale.US);
    ctx.setVariable("pageTitle", subject);
    ctx.setVariable("groupName", context.group().getName());
    ctx.setVariable("mainUrl", frontend);

    String content = emailTemplateEngine.process("group-deletion/template.html", ctx);
    return Email.builder().subject(subject).htmlBody(content).recipient(recipientEmail).build();
  }

  public Email createWelcomeEmail(NewGroupMembershipContext context, String recipientEmail) {
    String subject = String.format("Welcome to %s!", context.group().getName());
    Context ctx = new Context(Locale.US);
    ctx.setVariable("pageTitle", subject);
    ctx.setVariable("groupName", context.group().getName());
    ctx.setVariable("groupUrl", String.format("%s/group/%s", frontend, context.group().getId()));
    ctx.setVariable("mainUrl", frontend);

    String content = emailTemplateEngine.process("group-welcome/template.html", ctx);
    return Email.builder().subject(subject).htmlBody(content).recipient(recipientEmail).build();
  }

  public Email createGoodbyeEmail(DeletedGroupMembershipContext context, String recipientEmail) {
    String subject =
        String.format("Important: you have left the %s group.", context.group().getName());
    Context ctx = new Context(Locale.US);
    ctx.setVariable("pageTitle", subject);
    ctx.setVariable("groupName", context.group().getName());
    ctx.setVariable("groupUrl", String.format("%s/group/%s", frontend, context.group().getId()));
    ctx.setVariable("mainUrl", frontend);

    String content = emailTemplateEngine.process("group-goodbye/template.html", ctx);
    return Email.builder().subject(subject).htmlBody(content).recipient(recipientEmail).build();
  }
}
