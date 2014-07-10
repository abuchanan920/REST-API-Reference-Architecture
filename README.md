#Synopsis

A small POC application demonstrating the integration of a number of technologies into a REST API server.

##Build Tools

* [Gradle](http://www.gradle.org/) - a modern build too that is easier to extend than ant and more flexible than maven.
* [JaCoCo](http://www.eclemma.org/jacoco/) - a modern unit test code coverage tool
* [Sonar](http://www.sonarqube.org/) - provides nice reports on code quality and static code analysis

## Core

* [Jersey](https://jersey.java.net/) - the reference implementation of JAX-RS (i.e. REST web services)
* [Jetty](http://www.eclipse.org/jetty/) - an embeddable servlet container. Allows app to be self serving.
* [Dropwizard](https://dropwizard.github.io/dropwizard/) - integrates Jersey, Jetty, Metrics, and other useful tools.

## Operations

* [Metrics](http://metrics.codahale.com/) - provides easy to generate statistics on API performance and usage.

## Documentation

* [Swagger](https://helloreverb.com/developers/swagger) - provides a method of generating API documentation with a web based UI front end.

## Deployment

* [Docker](https://www.docker.io/) - the emerging standard for lightweight virtualization
* [CoreOS](http://coreos.com) - A leading linux environment for hosting Docker containers. Can be run on bare-metal, as a VM, or in the cloud.
* [etcd](http://coreos.com/using-coreos/etcd/) - A distributed key/value configuration store. Essentially a lighter-weight version of Zookeeper written in Go and integrated with CoreOS.

# Installation

## Requirements

User must have Java (7+) installed as well as [Gradle](http://www.gradle.org/).

On a Mac, you can most easily install gradle using [brew](http://brew.sh/): `brew install gradle`

## Quick Start

### Local
`gradle start` will build a single, self-contained, executable jar file that contains all of the app dependencies and put in in the ./build/libs directory and run it.

You can run it manually with `java -jar build/libs/rest-reference-1.0-SNAPSHOT.jar server src/main/resources/sample-config.yml`
after compiling with `gradle fatJar` if you prefer.

The API endpoint will be available at [http://localhost:8080](http://localhost:8080).

A console with access to the performance metrics, health checks, and thread dumps is at [http://localhost:8081](http://localhost:8081).

The API docs will be available at [http://localhost:8080/docs/](http://localhost:8080/docs/).

A computer consumable version of the docs are at [http://localhost:8080/api-docs](http://localhost:8080/api-docs).

### Docker container

#### Prerequisites

##### Docker
On a Mac, you can install using [brew](http://brew.sh/) via `brew install docker`.

This will install the client tools. Since Docker runs on top of Linux containers, you will be remotely controlling a Linux system. To set up your connection to where the server will be, you will then need to add `export DOCKER_HOST=172.17.8.100:4243` to your login profile (.zprofile or equivalent).

Instructions for other platforms are available at [http://docs.docker.io/introduction/get-docker/](http://docs.docker.io/introduction/get-docker/).

##### VirtualBox
This is the virtualization system we will run our CoreOS server within for this demo.

You can install from [https://www.virtualbox.org/](https://www.virtualbox.org/), or if you on a Mac and have [brew cask](http://caskroom.io/) installed, you can simply `brew cask install virtualbox`. If you already have brew, installing brew cask is simply `brew install caskroom/cask/brew-cask`.

##### Vagrant
This is a system that sits on top of VirtualBox to easily manage development environments.

You can install from [http://www.vagrantup.com/](http://www.vagrantup.com/), or if your are on a Mac and have [brew cask](http://caskroom.io/) installed, you can simply `brew cask install vagrant`.

##### CoreOS Utilities
Some other tools you will find handy on a Mac are etcdctl and fleetctl. These will allow you to interact with the CoreOS infrastructure directly from your desktop. You can install these on a Mac with brew with `brew install etcdctl` and `brew install fleetctl`. You will also want to add `export FLEETCTL_ENDPOINT=http://172.17.8.101:4001` to your login profile (.zprofile or equivalent).

#### Running

`gradle deployDocker` will compile the code, start a CoreOS virtual machine, create and deploy a Docker image of the application.

NOTE: The first time you run this may take a long time at "Pulling repository dockerfile/java". This is due to a slow upstream server, but fortunately is a one-time operation.

`gradle startDocker` will start a new container based on the deployed Docker image on the CoreOS virtual machine.

The API endpoint will be available at [http://172.17.8.101:8080](http://172.17.8.101:8080).

The console will be available at [http://172.17.8.101:8081](http://172.17.8.101:8081).

The API docs will be available at [http://172.17.8.101:8080/docs/](http://172.17.8.101:8080/docs/).

A computer consumable version of the docs are at [http://172.17.8.101:8080/api-docs](http://172.17.8.101:8080/api-docs).

#### Running Cluster
`gradle pushDocker` will tag the docker image and push it to a private docker registry that is running on one of the virtual machines (in a docker container of course).

`gradle initFleet` will upload the cluster configs in the fleet directory to the cluster to define which services to run.

`gradle startFleet` will activate those defined services on the cluster.

After a minute or so for the various machines to download the image from the private registry, they services will activate on the servers. You can see what is running where by running `fleetctl list-units`. Most likely the services will be running at 172.17.8.101 and 172.17.8.102. You will be able to access them with your web browser as above.

To test the system migration, `cd coreos; vagrant halt core-02`. The 172.17.8.102 virtual machine will shut down. If you run `fleetctl list-units` now, you should see that the cluster is starting another copy of the service on 172.17.8.103.

## Updating config

The application uses the specified config file as defaults, but looks to [etcd](http://coreos.com/using-coreos/etcd/) for overrides to these settings. This allows you to have the application automatically pick up the appropriate settings for its environment.

To see how this works:

1. Run the app in a Docker container with `gradle startDocker`
2. Go to [http://172.17.8.101:8081/config](http://172.17.8.101:8081/config) to see the current values of the app configuration settings.
3. Notice that the value of SwaggerBasePath is different than what is specified in the config file at src/dist/main/sample-config.yml. This is because we updated etcd with a different value when we started CoreOS (see sbin/start-coreos)
4. Take note the value of the current value of sampleConfigSetting. We will be modifying that below.
5. `cd coreos; vagrant ssh core-01 -c "etcdctl set /restreference/sampleConfigSetting newdatabase.hibu.com"` to update the sample database setting for the cluster.
6. Refresh your browser with the config settings to see that the updated setting has been detected by the app.

Note that this config change is persistent. If you shut down the CoreOS virtual machine and restart it later, you will see that this config setting remains.

Also note that this config setting is distributed (or would be if we were running more than one CoreOS instance). You don't need to change the setting on every server. It is distributed throughout the environment automatically.

The implementation provided will cache configuration information within the app process for performance and place watches on etcd to pick up changes automatically. The user should always use the value from the Configuration object when needed to be sure they are using the latest value. The user would need to check to make sure any persistent objects are still valid when used (e.g. a database connection), or a callback function could be added to the Configuration object to notify the app immediately that it should re-open a database connection (for example).


## Extended docs

* `gradle tasks` will list the various tasks you can run with gradle. Some highlights below.
* `gradle clean` will remove all build artifacts
* `gradle build` to just compile the code and execute unit tests
* `gradle sonarRunner` to analyze the code with sonar and generate the report. Note that you must have a sonar server running. On a mac you can again use brew `brew install sonar`
* `gradle jacocoTestReport` will generate a unit test coverage report and place it in the build/reports directory. Note that as of this writing (5/7/2014) the jacoco hasn't been updated to support Java 8, so if you are running that you will get an error here.
* `gradle eclipse` will generate the project files for Eclipse
* `gradle farJar` will build a single, self-contained, executable jar file that contains all of the app dependencies and put in in the ./build/libs directory.
* `gradle start` will run the app locally from the FatJar using the sample-config.yml settings.
* `gradle startCoreOS` will start the CoreOS virtual servers using VirtualBox
* `gradle stopCoreOS` will stop the CoreOS virtual servers
* `gradle destroyCoreOS` will top the CoreOS virtual servers and delete them
* `gradle deployDocker` will create a Docker image of the application and upload it to CoreOS.
* `gradle tagDocker` will tag the Docker image of the application
* `gradle pushDocker` will push the tagged Docker image to the private registry
* `gradle startDocker` will start a new container based on a previously deployed docker application image
* `gradle initFleet` will upload the cluster configuration units to the CoreOS cluster
* `gradle uninitFleet` will delete the cluster configuration units from the CoreOS cluster
* `gradle startFleet` will start the configured configuration units to form a clustered version of the app in the CoreOS cluster
* `gradle stopFleet` will stop the clustered version of the app
