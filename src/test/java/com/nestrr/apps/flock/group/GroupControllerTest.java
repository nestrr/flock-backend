package com.nestrr.apps.flock.group;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.repository.GroupInviteRepository;
import com.nestrr.apps.flock.group.repository.GroupRepository;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.shared.AuthenticatedTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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
}
