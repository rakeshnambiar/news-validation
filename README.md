# Getting started with Serenity and Cucumber

Serenity BDD is a library that makes it easier to write high quality automated acceptance tests, with powerful reporting and living documentation features. It has strong support for both web testing with Selenium, and API testing using RestAssured.

Serenity strongly encourages good test automation design, and supports several design patterns, including classic Page Objects, the newer Lean Page Objects/ Action Classes approach, and the more sophisticated and flexible Screenplay pattern.

The latest version of Serenity supports Cucumber 6.x.

## How to run the tests

- use the command `mvn clean verify -P cucumber-tests`
- to execute the tests in parallel, please use the command `mvn clean verify -P cucumber-tests -Dthreadcount=2`

## More about the framework

Visit the site https://serenity-bdd.info/ for more info

## Execution on Zalenium (scalabale selenium grid)

- Run the command `docker-compose up -d` from the root folder of the project
- Disable the `local webdriver` settings in the `serenity.conf` file
- Alternatively you can also run it through the command line like: `mvn clean verify -P cucumber-tests -Dtags=Regression -Dwebdriver.remote.url=http://<host-ip-address>:4444/wd/hub -Dwebdriver.remote.driver=chrome -Dwebdriver.remote.os=Linux`

## Execution on BrowserStack
- Enable the `browser stack webdriver settings` settings in the `serenity.conf` file
