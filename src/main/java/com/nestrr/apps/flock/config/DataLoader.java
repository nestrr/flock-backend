package com.nestrr.apps.flock.config;

import com.nestrr.apps.flock.campus.entity.Campus;
import com.nestrr.apps.flock.campus.repository.CampusRepository;
import com.nestrr.apps.flock.profile.entity.*;
import com.nestrr.apps.flock.profile.entity.id.TimeslotId;
import com.nestrr.apps.flock.profile.repository.*;
import com.nestrr.apps.flock.standing.entity.Standing;
import com.nestrr.apps.flock.standing.repository.StandingRepository;
import java.time.LocalTime;
import java.util.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(name = "spring.liquibase.contexts", havingValue = "dev, seed")
public class DataLoader implements ApplicationRunner {

  private final PersonRepository personRepository;
  private final StandingRepository standingRepository;
  private final DegreeViewRepository degreeViewRepository;
  private final RoleAssignmentRepository roleAssignmentRepository;
  private final RoleRepository roleRepository;
  private final CampusChoiceRepository campusChoiceRepository;
  private final CampusChoiceRankRepository campusChoiceRankRepository;
  private final CampusRepository campusRepository;
  private final TimeslotRepository timeslotRepository;

  public DataLoader(
      PersonRepository personRepository,
      StandingRepository standingRepository,
      DegreeViewRepository degreeViewRepository,
      RoleRepository roleRepository,
      RoleAssignmentRepository roleAssignmentRepository,
      CampusChoiceRepository campusChoiceRepository,
      CampusChoiceRankRepository campusChoiceRankRepository,
      CampusRepository campusRepository,
      TimeslotRepository timeslotRepository) {
    this.personRepository = personRepository;
    this.standingRepository = standingRepository;
    this.degreeViewRepository = degreeViewRepository;
    this.roleRepository = roleRepository;
    this.roleAssignmentRepository = roleAssignmentRepository;
    this.campusChoiceRepository = campusChoiceRepository;
    this.campusChoiceRankRepository = campusChoiceRankRepository;
    this.campusRepository = campusRepository;
    this.timeslotRepository = timeslotRepository;
  }

  public void createTimeslots(String personId) {
    Random random = new Random();
    // Create <= 10 timeslots.
    int timeslotsCount = random.nextInt(11);
    for (int i = 0; i < timeslotsCount; i++) {
      Integer day = random.nextInt(7);
      int startHour = random.nextInt(13);
      int endHour = random.nextInt(12, 24);
      int startMinute = random.nextInt(60);
      int endMinute = random.nextInt(60);
      int reliability = random.nextInt(3);
      int flexibility = random.nextInt(3);
      TimeslotId id =
          new TimeslotId(
              personId,
              day,
              LocalTime.of(startHour, startMinute),
              LocalTime.of(endHour, endMinute));
      timeslotRepository.save(new Timeslot(id, 0, reliability, flexibility));
    }
  }

  public void createCampusChoices(String personId, List<Campus> campuses) {
    Random random = new Random();
    int campusChoicesCount = random.nextInt(3);
    Set<Integer> usedCampuses = new HashSet<>();
    for (int i = 0; i < campusChoicesCount; i++) {
      int campusIndex = random.nextInt(campuses.size());
      while (usedCampuses.contains(campusIndex)) campusIndex = random.nextInt(campuses.size());

      Campus campus = campuses.get(campusIndex);
      usedCampuses.add(campusIndex);

      CampusChoice campusChoice =
          campusChoiceRepository.save(new CampusChoice(null, 0, personId, campus.getId()));
      campusChoiceRankRepository.save(new CampusChoiceRank(personId, i, campusChoice.getId()));
    }
  }

  public void setRole(String personId, List<Role> roles) {
    Optional<Role> role = roles.stream().filter(r -> r.getName().equals("student")).findAny();
    role.ifPresent(
        value -> roleAssignmentRepository.save(new RoleAssignment(personId, value.getId(), 0)));
  }

  public void setStanding(Person person, List<Standing> standings) {
    Random random = new Random();
    Standing standing = standings.get(random.nextInt(standings.size()));
    person.setStandingId(standing.getId());
    personRepository.save(person);
  }

  public Person setDegree(Person person, List<DegreeView> degrees) {
    Random random = new Random();
    DegreeView degree = degrees.get(random.nextInt(degrees.size()));
    person.setDegreeId(degree.getId());
    return personRepository.save(person);
  }

  @Transactional
  public void fillDetails(
      Person person,
      List<Standing> standings,
      List<DegreeView> degrees,
      List<Campus> campuses,
      List<Role> roles) {
    createTimeslots(person.getId());
    createCampusChoices(person.getId(), campuses);
    setRole(person.getId(), roles);
    person = setDegree(person, degrees);
    setStanding(person, standings);
  }

  public void run(ApplicationArguments args) {
    List<Standing> standings = standingRepository.findAll();
    List<DegreeView> degrees = degreeViewRepository.findAll(Sort.unsorted());
    List<Campus> campuses = campusRepository.findAll();
    List<Role> roles = roleRepository.findAll(Sort.unsorted());
    List<Person> persons = personRepository.findAll();
    for (Person person : persons) {
      fillDetails(person, standings, degrees, campuses, roles);
    }
  }
}
