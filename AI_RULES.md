# AI Coding Rules for WebGoat

## Project Overview

- WebGoat is a deliberately insecure Spring Boot web application (Java 25, Maven, `spring-boot-starter-parent` 4.1.0) used to teach application security. WebWolf, a companion app, is packaged in the same JAR.
- Java sources live in `src/main/java/org/owasp/webgoat` (container, lessons, server and webwolf packages). Lesson assets live in `src/main/resources/lessons`. Platform static assets and templates live in `src/main/resources/webgoat` and the WebWolf equivalents.
- Unit tests live in `src/test`. Integration and UI tests live in `src/it`.

## Lesson Code Versus Platform Code

- Lesson code is intentionally vulnerable. WHEN editing files under `src/main/java/org/owasp/webgoat/lessons/` or `src/main/resources/lessons/`, do not "fix" the vulnerability that the lesson teaches. This includes the string-concatenated SQL in `SqlInjectionLesson9.java` and `SqlInjectionLesson10.java`, the unsafe `readObject` usage in `InsecureDeserializationTask.java`, `SerializationHelper.java` and `org/dummy/insecure/framework/VulnerableTaskHolder.java`, the weak hashing and `java.util.Random` in `HashingAssignment.java` and `CSRFGetFlag.java`, and the client-side `innerHTML` use in `HtmlTampering.html`.
- WHEN adding a new lesson, keep the vulnerability confined to the lesson package and its assets. Do not reuse vulnerable helpers in platform code.
- WHEN editing platform code outside the lessons packages (server, container, webwolf, Spring Security configuration), use parameterized queries with `PreparedStatement`, avoid `ObjectInputStream` on untrusted data, use `SecureRandom` for tokens and secrets, avoid MD5 and SHA-1 for security purposes, and do not assign untrusted data to `innerHTML`.
- NEVER hardcode real credentials, keys or tokens in any source, resource, Dockerfile or workflow file.

## Dependency Rules

- WHEN editing `pom.xml`, keep shared versions in the `<properties>` section and reference them with `${...}`, consistent with the existing style. Keep Spring Boot starters without explicit versions so they stay managed by the parent BOM.
- Do NOT update versions that are commented as "do not update necessary for lesson" (for example `commons-collections.version`, `zxcvbn.version`) or that exist to support a vulnerable-components lesson (`xstream.version` 1.4.5, `jjwt.version` 0.9.1). Preserve the comments next to them.
- Keep Jackson 2 (`com.fasterxml.jackson`) dependencies on the classpath alongside the Spring Boot 4 default, as documented in the comment in `pom.xml`.
- Keep the `maven-enforcer-plugin` rule that bans `org.apache.logging.log4j:log4j-core`.
- WHEN adding a dependency outside lesson needs, choose a specific non-vulnerable version, and use the `owasp` profile (`dependency-check-maven`, `failBuildOnCVSS` 7) as the repository's existing vulnerability gate.

## Code Style Rules

- WHEN writing or editing Java files under `src/main/java`, `src/test/java` and `src/it/java`, follow the Spotless configuration in `pom.xml` (Google Java format, license header from `config/license-headers/java`, unused imports removed) and the Checkstyle configuration in `config/checkstyle/`.
- WHEN editing `pom.xml`, keep the Spotless `sortPom` ordering and two-space indentation.
- Use Lombok annotations consistently with the existing code; keep the Lombok version in both the dependency and the `annotationProcessorPaths` entry in sync.

## Testing Rules

- WHEN adding unit tests, place them under `src/test/java` and use the existing Spring Boot test starters, `spring-security-test`, and WireMock.
- WHEN adding integration or UI tests, place them under `src/it/java`. Name them so they match the failsafe includes (`org/owasp/webgoat/integration/*Test` and `org/owasp/webgoat/playwright/**/*Test`) and keep them excluded from surefire (`*IntegrationTest.java`, `*UITest.java`).
- Keep the `--add-opens` surefire arguments that the vulnerable components lesson requires.

## Container Rules

- WHEN editing `Dockerfile`, keep running the application as a non-root user and keep the health check. Keep the pinned `eclipse-temurin` base image tag instead of switching to a floating tag.
- WHEN editing `Dockerfile_desktop`, keep the pinned OWASP ZAP and base image references and do not add credentials.

## CI/CD Workflow Rules

- WHEN editing `.github/workflows/build.yml`, preserve the pre-commit check job and the Maven `verify` build across the Windows, Ubuntu and macOS matrix.
- WHEN editing `.github/dependabot.yml`, do not remove existing update entries.
- NEVER hardcode tokens in workflow files. Reference them through `${{ secrets.* }}`.

## Prohibited Repo Patterns

- Fixing intentionally vulnerable lesson code as if it were a defect
- Copying vulnerable lesson code patterns into platform code
- Upgrading dependencies that are marked as pinned for a lesson
- Adding `log4j-core` as a dependency
- Hardcoded secrets in any committed file
