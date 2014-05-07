## Synopsis

A small POC application demonstrating the integration of a number of technologies into a REST API server.

# Build Tools
	* [Gradle](http://www.gradle.org/) - a modern build too that is easier to extend than ant and more flexible than maven.
	* [JaCoCo](http://www.eclemma.org/jacoco/) - a modern unit test code coverage tool
	* [Sonar](http://www.sonarqube.org/) - provides nice reports on code quality and static code analysis

# Core
	* [Jersey](https://jersey.java.net/) - the reference implementation of JAX-RS (i.e. REST web services)
	* [Jetty](http://www.eclipse.org/jetty/) - an embeddable servlet container. Allows app to be self serving.
	* [Dropwizard](https://dropwizard.github.io/dropwizard/) - integrates Jersey, Jetty, Metrics, and other useful tools.

# Operations
	* [Metrics](http://metrics.codahale.com/) - provides easy to generate statistics on API performance and usage.

# Documentation
	* [Swagger](https://helloreverb.com/developers/swagger) - provides a method of generating API documentation with a web based UI front end.

# Deployment
	* [Docker](https://www.docker.io/) - the emerging standard for lightweight virtualization

## Installation

# Requirements
User must have Java (7+) installed as well as Gradle.

On a Mac, you can most easily install gradle using [brew](http://brew.sh/): `brew install gradle`

# Quick Start
`gradle start` will build a single, self-contained, executable jar file that contains all of the app dependencies and put in in the ./build/libs directory and run it.

You can run it manually with `java -jar build/libs/rest-reference-1.0-SNAPSHOT.jar server src/main/resources/sample-config.yml`
after compiling with `gradle fatJar` if you prefer.

The API will be available at http://localhost:8080
The API docs will be available at http://localhost:8080/docs/
A computer consumable version of the docs are at http://localhost:8080/api-docs
A console with access to the performance metrics, health checks, and thread dumps is at http://localhost:8081

# Extended docs
* `gradle tasks` will list the various tasks you can run with gradle. Some highlights below.
* `gradle clean` will remove all build artifacts
* `gradle build` to just compile the code and execute unit tests
* `gradle sonarRunner` to analyze the code with sonar and generate the report. Note that you must have a sonar server running. On a mac you can again use brew `brew install sonar`
* `gradle jacocoTestReport` will generate a unit test coverage report and place it in the build/reports directory. Note that as of this writing (5/7/2014) the jacoco hasn't been updated to support Java 8, so if you are running that you will get an error here.
* `gradle eclipse` will generate the project files for Eclipse
* `gradle farJar` will build a single, self-contained, executable jar file that contains all of the app dependencies and put in in the ./build/libs directory.