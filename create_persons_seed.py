#!/usr/bin/env python3
import csv
import json
import random

import requests
from faker import Faker


# out: src/main/resources/db/changelog/seed/dev_persons.csv
# out: src/main/resources/
class PersonsSeed:
    def __init__(self, count: int):
        self.profilesCount = count
        self.faker = Faker()
        self.PICTURES_URL = 'https://randomuser.me/api/?inc=picture'
        self.SEED_BASE_PATH = 'src/main/resources/db/changelog/seed'
        self.PERSON_COLUMNS = ['id', 'version', 'name', 'email', 'image', 'bio', 'standing_id', 'degree_id',
                               'last_login']

    def getProfileImage(self):
        response = requests.get(self.PICTURES_URL)
        result = json.loads(response.text)['results'][0]
        return result['picture']['medium']

    def getEmail(self, used_emails: set[str]):
        email = self.faker.email(True, 'notoit.edu')
        while email in used_emails:
            prefix = email.split('@')[0] + str(random.randint(0, 9999))
            email = "{}@notoit.edu".format(prefix)
        return email

    def createPersons(self, fieldnames: list[str]):
        persons = []
        used_emails = set([])
        for i in range(self.profilesCount):
            person = {'id': self.faker.uuid4(),
                      'version': 0,
                      'name': self.faker.name(),
                      'email': self.getEmail(used_emails),
                      'image': self.getProfileImage() if random.randint(0, 1) == 1 else "null",
                      'bio': "".join(self.faker.paragraphs(nb=random.randint(3, 5))) if random.randint(0,
                                                                                                       1) == 1 else "null",
                      'last_login': self.faker.date_time() if random.randint(0, 1) == 1 else "null"}

            other_fields = [f for f in fieldnames if person.keys().__contains__(f) is False]
            for f in other_fields:
                person[f] = "null"
            persons.append(person)
            used_emails.add(person['email'])
        return persons

    def write(self):
        fieldnames = self.PERSON_COLUMNS
        persons = self.createPersons(fieldnames)
        filename = '{}/dev_persons.csv'.format(self.SEED_BASE_PATH)
        with open(filename, 'w', newline='') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames, quoting=csv.QUOTE_NONNUMERIC, quotechar='"')
            writer.writeheader()
            for person in persons:
                writer.writerow(person)


def main():
    persons_seed = PersonsSeed(1000)
    print(persons_seed.write())


if __name__ == "__main__":
    main()
