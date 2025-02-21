## Flock Backend

This is the repository that hosts the Flock app's backend.

## Technologies

See Confluence for the reasoning behind these choices.

* Spring Boot 3.3.6
* Java 21
* PostgreSQL
    * Flock uses [Supabase](https://supabase.com/)'s managed Postgres database service, as that saves time setting up
      and managing a self-hosted database.
* Docker with GitHub Container Registry
    * See tagging information under next section.
* GitHub Actions for CI/CD management
    * SonarQube runs code quality and coverage checks on PRs to `develop` and `main`.
    * Images of the application are built and published on push to `develop` and `main`. They are tagged `canary` and
      `latest` respectively.
    * Nightly integration test runs will start from 02/24 onwards.
* [Wiremock Cloud](https://nestrr.wiremockapi.cloud/) for mocking OAuth/OIDC accurately during integration tests.
* [Liquibase](https://www.liquibase.com/) for versioned and declarative management of database schema.
* [Doppler](https://www.doppler.com/) for secrets management.

## Development Process

See Confluence for the reasoning behind these decisions.

* We use [Jira](https://maryamdev.atlassian.net/) for issue management, using Kanban-style project management. We use
  Jira's GitHub integration to automatically track issue progress in this repository as well as any other repositories.
* We use [Zulip](https://nestrr.zulipchat.com) for messaging and deployment/moderation/bug report/feature request
  alerts.
* Pull requests must be approved by codeowners. Changes are squashed to `develop`, and then `main` is rebased onto
  `develop` to align the two branches without polluting commit history with unnecessary PRs.
* We use Confluence to document design decisions and information about relevant technology to the Flock project as a
  whole.

## Development Standards

This is a work in progress. Check back for more.

* Name your branches using the issue number in Jira (e.g. `floc-30`). This keeps Jira up to date on your progress.
* Test your code and make well-written PRs, so that it is easier to track changes.
* Make clear commit messages. This is a
  good [primer](https://www.freecodecamp.org/news/how-to-write-better-git-commit-messages/) on the topic.

## Get Started

Thanks for helping out! To get started on development, complete these steps:

* Ensure you have Java 21 installed on your machine. You can use SDKMAN to easily switch Java versions on your machine.
* Join the Jira organization so you can track your progress and new tasks.
* Install Doppler on your machine.
    * Run `doppler setup`, and confirm when prompted. This will automatically set up secrets in your environment using
      `doppler.yaml`.
    * Ensure you have access to the Doppler configs first! Reach out if it's giving you trouble.
* If you are using IntelliJ, you can use the run configurations published under `.run` to automatically inject the
  secrets in Doppler when running/testing/debugging the application. Otherwise, prefix all your Maven commands like so:
  `doppler run -- mvn <your commands>`.
* If you haven't already, join Zulip as well!