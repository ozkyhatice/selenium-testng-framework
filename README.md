# Selenium + TestNG Sample (SauceDemo)

A simple Selenium + TestNG project that automates login and product flows on the SauceDemo site.

## Requirements
- Java 11+
- Maven
- Google Chrome and ChromeDriver (must be on your PATH)

## Run
1) Open the repo: `cd /Users/hatice/Downloads/seleni`
2) Download dependencies and run tests: `mvn test`

TestNG runs the classes listed in `testng.xml` (`InventoryTest`, `LoginTestPom`). Tests start Chrome in headless mode by default.

## CI
- GitHub Actions workflow: `.github/workflows/ci.yml`
- Triggers on push/PR to `master`
- Uses Ubuntu with Java 11, installs Chrome/ChromeDriver, then runs `mvn clean compile` and `mvn test -DsuiteXmlFile=testng.xml`
- Uploads reports from `target/surefire-reports/` as an artifact

## Tech Stack (Details)
- Language: Java 11
- Build: Maven (`pom.xml`)
- Testing: TestNG  (`testng.xml` suite)
- Browser automation: Selenium Java  (ChromeDriver, headless Chrome defaults)
- Patterns: Page Object Model (`LoginPagePom`, `InventoryPage`)
- CI: GitHub Actions (Ubuntu runner, `actions/setup-java`, `browser-actions/setup-chrome`)
- Reports: Surefire / TestNG reports under `target/surefire-reports/`

## Folders
- `src/main/java`: Simple app entry (`App.java`)
- `src/test/java`: Selenium + TestNG tests
  - `InventoryTest`: price checks, add-to-cart, UI checks
  - `LoginTestPom`: login scenario with POM
- `pom.xml`: Maven deps (Selenium 4.18.1, TestNG 7.9.0)
- `testng.xml`: Test classes to run

## Helpful Notes
- Target site: https://www.saucedemo.com/
- Default user: `standard_user / secret_sauce`
- Reports are generated under `target/surefire-reports`.

