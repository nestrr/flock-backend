package com.nestrr.apps.flock.group;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.group.constants.GroupInviteConstants;
import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.GroupInviteStatus;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import com.nestrr.apps.flock.group.repository.GroupInviteRepository;
import com.nestrr.apps.flock.group.repository.GroupInviteStatusRepository;
import com.nestrr.apps.flock.group.repository.GroupRepository;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.shared.AuthenticatedTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroupControllerTest extends AuthenticatedTest {
  @Autowired private GroupRepository groupRepository;
  @Autowired private GroupInviteRepository groupInviteRepository;
  @Autowired private GroupInviteStatusRepository groupInviteStatusRepository;
  @Autowired private PersonRepository personRepository;

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
  }

  @Test
  void canGetGroups() {
    JsonPath jsonPath =
        given()
            .port(getPort())
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + getBearerToken())
            .when()
            .get("/group/me")
            .getBody()
            .jsonPath();
    List<GroupDto> groups = jsonPath.getList("$");
    Assertions.assertEquals(0, groups.size());
  }

  @Test
  @Disabled(value = "Need to set up alternate sender email config for tests to use.")
  void canCreateGroup() {
    List<Person> members =
        List.of(
            Person.builder().id("a").email("a@gmail.com").name("a").build(),
            Person.builder().id("b").email("b@gmail.com").name("b").build(),
            Person.builder().id("c").email("c@gmail.com").name("c").build());
    personRepository.saveAll(members);
    NewGroupRequest request =
        new NewGroupRequest(
            members.stream().map(Person::getId).toList(),
            "Test group",
            "Test description",
            "Test image");
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .body(request)
        .post("/group")
        .then()
        .statusCode(HttpStatus.OK.value());

    List<Group> groupsCreated = groupRepository.findByAdminId(getUserId());
    Assertions.assertFalse(groupsCreated.isEmpty());

    List<GroupInvite> groupInvitesCreated =
        groupInviteRepository.findByIdGroupId(groupsCreated.getFirst().getId());
    Assertions.assertEquals(members.size(), groupInvitesCreated.size());
  }

  @Test
  @Disabled(value = "Need to set up alternate sender email config for tests to use.")
  void canDeleteGroup() {
    NewGroupRequest request =
        new NewGroupRequest(
            List.of(), "Test group for group deletion", "Test description", "Test image");
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .body(request)
        .post("/group")
        .then()
        .statusCode(HttpStatus.OK.value());

    Group group =
        groupRepository.findByAdminId(getUserId()).stream()
            .filter(g -> g.getName().equals(request.name()))
            .toList()
            .getFirst();
    given()
        .port(getPort())
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .delete(String.format("/group/%s", group.getId()))
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
    Assertions.assertTrue(groupRepository.findById(group.getId()).isEmpty());
  }

  @Test
  void canUpdateGroup() {
    NewGroupRequest request =
        new NewGroupRequest(
            List.of(), "Test group for group deletion", "Test description", "Test image");
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .body(request)
        .post("/group")
        .then()
        .statusCode(HttpStatus.OK.value());
    UpdateGroupRequest updateRequest =
        new UpdateGroupRequest(
            getUserId(), "new name", "my new group description!!", "someimage.com/image");
    Group group =
        groupRepository.findByAdminId(getUserId()).stream()
            .filter(g -> g.getName().equals(request.name()))
            .toList()
            .getFirst();
    given()
        .port(getPort())
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .patch(String.format("/group/%s", group.getId()))
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  @Disabled(value = "Need to set up alternate sender email config for tests to use.")
  void canInviteUserToGroup() {
    NewGroupRequest request =
        new NewGroupRequest(
            List.of(), "Test group for single invite", "Test description", "Test image");
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .body(request)
        .post("/group")
        .then()
        .statusCode(HttpStatus.OK.value());
    Group group =
        groupRepository.findByAdminId(getUserId()).stream()
            .filter(g -> g.getName().equals(request.name()))
            .toList()
            .getFirst();
    Person person =
        personRepository.save(
            Person.builder().id("Some id").name("some name").email("an email").build());
    given()
        .port(getPort())
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .contentType(ContentType.JSON)
        .post(String.format("/group-invite/%s/member/%s", group.getId(), person.getId()))
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  void cannotInviteUserIfNotMember() {
    String currentUserToken = getBearerToken();
    Person member =
        personRepository.save(
            Person.builder()
                .id("randomid123")
                .email("member@gmail.com")
                .name("Group member")
                .build());
    String outsiderToken = getAuthenticator().of("outsider@gmail.com", "password").getBearerToken();
    NewGroupRequest request =
        new NewGroupRequest(
            List.of(),
            "Test group for cannotInviteUserIfNotMember",
            "Test description",
            "Test image");
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + currentUserToken)
        .when()
        .body(request)
        .post("/group")
        .then()
        .statusCode(HttpStatus.OK.value());
    Group group =
        groupRepository.findByAdminId(getUserId()).stream()
            .filter(g -> g.getName().equals(request.name()))
            .toList()
            .getFirst();
    given()
        .port(getPort())
        .header("Authorization", "Bearer " + outsiderToken)
        .when()
        .contentType(ContentType.JSON)
        .post(String.format("/group-invite/%s/member/%s", group.getId(), member.getId()))
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  void cannotBatchInviteUserIfNotMember() {
    String activeUserToken = getBearerToken();
    Person currentMember =
        personRepository.save(
            Person.builder()
                .id("randomid456")
                .email("member2@gmail.com")
                .name("Group member")
                .build());
    String outsiderToken = getAuthenticator().of("outsider@gmail.com", "password").getBearerToken();
    NewGroupRequest request =
        new NewGroupRequest(
            List.of(),
            "Test group for cannotInviteUserIfNotMember",
            "Test description",
            "Test image");
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + activeUserToken)
        .when()
        .body(request)
        .post("/group")
        .then()
        .statusCode(HttpStatus.OK.value());
    Group group =
        groupRepository.findByAdminId(getUserId()).stream()
            .filter(g -> g.getName().equals(request.name()))
            .toList()
            .getFirst();
    given()
        .port(getPort())
        .header("Authorization", "Bearer " + outsiderToken)
        .when()
        .contentType(ContentType.JSON)
        .body(List.of("memberId1", "memberId2"))
        .post(String.format("/group-invite/%s/member", group.getId()))
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value());
  }

  @Test
  @Disabled
  void canAcceptInvite() {
    Person acceptingInvitee =
        Person.builder()
            .id("acceptinginvitee123")
            .name("A random")
            .email("accepter@random.xyz")
            .build();
    String accepterToken =
        getAuthenticator().of(acceptingInvitee.getEmail(), "inviteepass").getBearerToken();
    Person rejectingInvitee =
        Person.builder()
            .id("rejectinginvitee123")
            .name("A random")
            .email("rejecter@random.xyz")
            .build();
    String rejecterToken =
        getAuthenticator().of(rejectingInvitee.getEmail(), "inviteepass").getBearerToken();

    // Create group
    NewGroupRequest request =
        new NewGroupRequest(
            List.of(), "Test group for canRespondToInvite", "Test description", "Test image");
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getUserId())
        .when()
        .body(request)
        .post("/group")
        .then()
        .statusCode(HttpStatus.OK.value());
    Group group =
        groupRepository.findByAdminId(getUserId()).stream()
            .filter(g -> g.getName().equals(request.name()))
            .toList()
            .getFirst();

    // Get invite status IDs
    String pending =
        groupInviteStatusRepository
            .findByName(GroupInviteStatuses.RESPONSE_PENDING.value())
            .map(GroupInviteStatus::getName)
            .orElse("");
    String accepted =
        groupInviteStatusRepository
            .findByName(GroupInviteStatuses.ACCEPTED.value())
            .map(GroupInviteStatus::getName)
            .orElse("");
    String rejected =
        groupInviteStatusRepository
            .findByName(GroupInviteStatuses.REJECTED.value())
            .map(GroupInviteStatus::getName)
            .orElse("");

    // Create invite records
    groupInviteRepository.save(
        GroupInvite.builder()
            .id(new GroupInviteId(group.getId(), acceptingInvitee.getId()))
            .statusId(pending)
            .expiresOn(LocalDateTime.now().plusDays(GroupInviteConstants.INVITE_WAIT_PERIOD_DAYS))
            .build());
    groupInviteRepository.save(
        GroupInvite.builder()
            .id(new GroupInviteId(group.getId(), rejectingInvitee.getId()))
            .statusId(pending)
            .expiresOn(LocalDateTime.now().plusDays(GroupInviteConstants.INVITE_WAIT_PERIOD_DAYS))
            .build());

    // Respond to invite
    given()
        .port(getPort())
        .header("Authorization", "Bearer " + accepterToken)
        .when()
        .contentType(ContentType.JSON)
        .patch(
            String.format(
                "/group-invite/%s/member/%s?statusId=%s",
                group.getId(), acceptingInvitee.getId(), accepted))
        .then()
        .statusCode(HttpStatus.OK.value());
    given()
        .port(getPort())
        .header("Authorization", "Bearer " + accepterToken)
        .when()
        .contentType(ContentType.JSON)
        .patch(
            String.format(
                "/group-invite/%s/member/%s?statusId=%s",
                group.getId(), rejectingInvitee.getId(), rejected))
        .then()
        .statusCode(HttpStatus.OK.value());
  }
}
