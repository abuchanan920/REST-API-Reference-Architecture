#Getting Started

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
* [Logstash](http://logstash.net) - tool for managing events and logs
* [Kibana](http://www.elasticsearch.org/overview/kibana/) - web interface for real-time analyis of log data

## Documentation

* [Swagger](https://helloreverb.com/developers/swagger) - provides a method of generating API documentation with a web based UI front end.

## Deployment

* [Docker](https://www.docker.io/) - the emerging standard for lightweight virtualization
* [CoreOS](http://coreos.com) - A leading linux environment for hosting Docker containers. Can be run on bare-metal, as a VM, or in the cloud.
* [etcd](http://coreos.com/using-coreos/etcd/) - A distributed key/value configuration store. Essentially a lighter-weight version of Zookeeper written in Go and integrated with CoreOS.
* [haproxy](http://www.haproxy.org/) - A reliable, high performance TCP/HTTP load balancer

# Installation

## Requirements

### Java
User must have Java (7+) installed.

### Go

User needs to have the Go language installed. On a Mac, you can install using [brew](http://brew.sh/) via `brew install go`.

### Gradle

On a Mac, you can most easily install [Gradle](http://www.gradle.org/) using [brew](http://brew.sh/): `brew install gradle`

### Docker

On a Mac, you can install using [brew](http://brew.sh/) via `brew install docker`.

NOTE: As of this writing (8/20/2014), you will need the 1.0.1 version of docker on your laptop. Newer versions (1.1.0+) cannot talk to the version of Docker on the CoreOS virtual machines. You can do a `cd $(brew --prefix );git checkout 7666e02 Library/Formula/docker.rb;brew install docker` and a `brew switch docker 1.0.1` to configure your machine to use the correct version. This should cease to be a problem with an upcoming CoreOS release.

This will install the client tools. Since Docker runs on top of Linux containers, you will be remotely controlling a Linux system. To set up your connection to where the server will be, you will then need to add `export DOCKER_HOST=172.17.8.100:4243` to your login profile (.zprofile or equivalent).

Instructions for other platforms are available at [http://docs.docker.io/introduction/get-docker/](http://docs.docker.io/introduction/get-docker/).

#### VirtualBox
This is the virtualization system we will run our CoreOS server within for this demo.

You can install from [https://www.virtualbox.org/](https://www.virtualbox.org/), or if you on a Mac and have [brew cask](http://caskroom.io/) installed, you can simply `brew cask install virtualbox`. If you already have brew, installing brew cask is simply `brew install caskroom/cask/brew-cask`.

#### Vagrant
This is a system that sits on top of VirtualBox to easily manage development environments.

You can install from [http://www.vagrantup.com/](http://www.vagrantup.com/), or if your are on a Mac and have [brew cask](http://caskroom.io/) installed, you can simply `brew cask install vagrant`.

#### Hosts File
Some of these services will need to be able to speak to each other by name. I spent some time trying to get Vagrant to fake this out to avoid editing the hosts file, but to no avail. You will want to add the following to your /etc/hosts file (or equivalent):

````
172.17.8.101 core-01 rest logstash
172.17.8.102 core-02
172.17.8.103 core-03
````

## Quick Start
### Starting the Cluster
First, run `. ./setenv.sh` to configure the paths and environment variables for the demo.

Then, `gradle startDemo` will execute everything to get your cluster up and running. This will take a while (perhaps as long as 30 mins depending on download speeds, etc.).

It will:

1. Start a cluster of 4 machines using Vagrant. One machine is just to host a private Docker registry. The other 3 will run as a cluster with etcd and fleetd.
2. Build and deploy an HAProxy container to the cluster. 
3. Build and deploy Logstash and Logstash-Forwarder containers to the cluster.
4. Build and deploy containers of the REST API example app.


The API endpoint will be available at [https://rest](https://rest).

The API docs will be available at [https://rest/docs/](https://rest/docs/).

A computer consumable version of the docs are at [http://rest/api-docs](http://rest/api-docs).

The individual consoles will be available at [http://172.17.8.101:8081](http://172.17.8.101:8081). Note that you will need to use a valid IP address depending on what cores the app is running on. See `fleetctl list-units` to tell.

The Kibana console will be available at [http://logstash:9292](http://logstash:9292)

The load balancer status will be available at [http://rest:8080/stats](http://rest:8080/stats). The username is "username" and the password is "password"

### Shutting the Cluster down
Run `gradle stopCoreOS` to stop the cluster. You can restart again with `gradle startCoreOS`. 

If you want to completely destroy the cluster, run `gradle destroyCoreOS`.

### Running Local Standalone (for debugging, etc.)
`gradle start` will build a single, self-contained, executable jar file that contains all of the app dependencies and put in in the ./build/libs directory and run it.

You can run it manually with `java -jar build/libs/rest-reference-1.0-SNAPSHOT.jar server src/main/resources/sample-config.yml`
after compiling with `gradle fatJar` if you prefer.

The API endpoint will be available at [http://localhost:8080](http://localhost:8080).

A console with access to the performance metrics, health checks, and thread dumps is at [http://localhost:8081](http://localhost:8081).

The API docs will be available at [http://localhost:8080/docs/](http://localhost:8080/docs/).

A computer consumable version of the docs are at [http://localhost:8080/api-docs](http://localhost:8080/api-docs).

# Things to See

## Force Load Balancer to a Particular Core
Since we are using a hosts file for mapping the IP address to the service, we need to make sure the load balancer is running on a particular core (in this case core-01). This was done using fleet metadata.

## Load Balancer Test
The load balancer is watching the healthcheck page to make sure a node is healthy enough to send traffic to it.

Go to the load balancer console at [http://rest:8080/stats](http://rest:8080/stats) and note the bottom where it should show 2 nodes in a green/active state.

The application has a simple health check that says "if I have processed the insert of more than 3 items, I am now broken". 
 
To test this, go to the [https://rest/docs/](https://rest/docs/) page and POST 7 values into the system using the API. The load balancer will distribute them across the 2 nodes, causing one of them to become overfull. 

You can then go to the load balancer console at [http://rest:8080/stats](http://rest:8080/stats) and see that one has dropped with a failure status.

## System Migration Test
The load balancer is using service discovery through etcd to determine which nodes are currently available (healthchecks notwithstanding).

Let's test the system migration by shutting down one of the cores and watching the service migrate. A new api service container should start on another available node, and the load balancer should detect the loss of the one and the creation of the other.

First, look at `fleetctl list-units` and see which cores the restreference-api services are running on. You can tell from the IP address at the end of the line. 101 maps to core-01, 102 maps to core-02, 103 maps to core-03. Preferably find the one that is in a failed mode in the load balancer, but if it is running on core-01, choose the other because we have the load balancer running on core-01 and we don't want to shut that one down. 

The following instructions will assume you are shutting down core-02. Adjust accordingly if you are shutting down core-03.

To test the system migration, `cd coreos; vagrant halt core-02`. The 172.17.8.102 virtual machine will shut down. If you run `fleetctl list-units` now, you should see that the cluster is starting another copy of the service on one of the other cores (this might take a few seconds).

Go back to the load balancer console at [http://rest:8080/stats](http://rest:8080/stats) and see that the old container disappears and the new one is found. If you had to shut the good one down before, you might not see any visible change here, but note that your service is still up and running on [https://rest/docs/](https://rest/docs/).

Note that when an image is being started for the first time on a core, it can take a few seconds for it to download from the private registry.

## Updating config

The application uses the specified config file as defaults, but looks to [etcd](http://coreos.com/using-coreos/etcd/) for overrides to these settings. This allows you to have the application automatically pick up the appropriate settings for its environment.

To see how this works:

1. Find an IP address for a restreference-api container using `fleetctl list-units` and looking at the end of the line.
2. Using one of the IP address you found, go to [http://172.17.8.101:18081/config](http://172.17.8.101:18081/config) (replace with your IP as needed) to see the current values of the app configuration settings.
3. Notice that the value of SwaggerBasePath is different than what is specified in the config file at src/dist/main/sample-config.yml. This is because we updated etcd with a different value when we started CoreOS (see coreos/start.sh)
4. Take note of the value of the current value of sampleConfigSetting. We will be modifying that below.
5. Again, using the IP address you found, run `etcdctl --peers 172.17.8.101:4001 set restreference/sampleConfigSetting newdatabase.hibu.com` to update the sample database setting for the cluster.
6. Refresh your browser with the config settings to see that the updated setting has been detected by the app.
7. Replace the IP address in your browser with the IP address of the other container and check the settings there. You will see that the change was implemented cluster-wide! You don't need to change the setting on every server.

Note that this config change is persistent. If you shut down the CoreOS virtual machine and restart it later, you will see that this config setting remains.

The implementation provided will cache configuration information within the app process for performance and place watches on etcd to pick up changes automatically. The user should always use the value from the Configuration object when needed to be sure they are using the latest value. The user would need to check to make sure any persistent objects are still valid when used (e.g. a database connection), or a callback function could be added to the Configuration object to notify the app immediately that it should re-open a database connection (for example).

## Look at Logs
Go to the Kibana Logstash console at [http://logstash:9292/index.html#/dashboard/file/logstash.json](http://logstash:9292/index.html#/dashboard/file/logstash.json).

You should see a lot of log activity, mostly calls to the healthcheck page.

You can try searching and filtering the logs by entering something like `!healthcheck` into the query box at the top of the screen.

You can experiment with searching and filtering the logs from here.

**Note that the Logstash-Forwarders weren't specifically configured with any knowledge of the containers they are processing. This is all done by runtime inspection. Any new containers that start will also have their logs detected and forwarded.** 

**Additionally, the application containers themselves have no knowledge that their logs are being consolidated. It is transparent to the application itself.**

## Extended docs

* `gradle tasks` will list the various tasks you can run with gradle. Some highlights below.
* `gradle clean` will remove all build artifacts
* `gradle build` to just compile the code and execute unit tests
* `gradle sonarRunner` to analyze the code with sonar and generate the report. Note that you must have a sonar server running. On a mac you can again use brew `brew install sonar`
* `gradle jacocoTestReport` will generate a unit test coverage report and place it in the build/reports directory. Note that as of this writing (5/7/2014) the jacoco hasn't been updated to support Java 8, so if you are running that you will get an error here.
* `gradle eclipse` will generate the project files for Eclipse
* `gradle farJar` will build a single, self-contained, executable jar file that contains all of the app dependencies and put in in the ./build/libs directory.
* `gradle start` will run the app locally from the FatJar using the sample-config.yml settings.
* `gradle compileEtcdCtl` will build the local copy of the etcdctl utility
* `gradle compileFleetCtl` will build the local copy of the fleetctl utility
* `gradle startCoreOS` will start the CoreOS virtual servers using VirtualBox
* `gradle stopCoreOS` will stop the CoreOS virtual servers
* `gradle destroyCoreOS` will top the CoreOS virtual servers and delete them
* `gradle distDocker` will build the Dockerfile and inputs for creating a Docker container of the application
* `gradle deployDocker` will create a Docker image of the application and upload it to CoreOS.
* `gradle tagDocker` will tag the Docker image of the application with the application name label so it can be referred to by name rather than ID.
* `gradle pushDocker` will push the tagged Docker image to the private registry
* `gradle deployHAProxy` will create a load balancer Docker image and push it to the private registry.
* `gradle deployLogstash` will create a Logstash Docker image and push it to the private repository.
* `gradle deployLogstashForwarder` will create a Logstash-Forwarder Docker image and push it to the private repository.
* `gradle initFleet` will upload the cluster configuration units to the CoreOS cluster
* `gradle uninitFleet` will delete the cluster configuration units from the CoreOS cluster
* `gradle startFleet` will start the configured configuration units to form a clustered version of the app in the CoreOS cluster
* `gradle stopFleet` will stop the clustered version of the app
* `gradle updateDockerImages` will make every core pull the latest version of any installed images from the private registry.
* `gradle reapStaleDockerContainers` a utility task to clear out any Docker containers in the cluster that have exited, but have not been removed.
* `gradle startDemo` will configure and start the cluster, as well as build, push, configure, and start all of the needed Docker containers.
