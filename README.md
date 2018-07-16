# CI SERVER with Jenkins + NGINX + Docker
Create a continuous integration server farm with Jenkins, NGINX and Docker

## Getting Started
These instructions will help you to set up a continuous integration server using Jenkins on top of Docker fronted by an NGINX web proxy to build you CI-CD pipelines.

### Prerequisites
The following software must be installed on your server or local computer

 * [Docker v18.04.0-ce](https://docs.docker.com/install/)
 * [Docker compose 1.19.0](https://docs.docker.com/compose/install/)
 * GNU Make 4.2.1 - Installation may exist in the default packages of your linux distribution, if not, installations may vary depending of the host O.S.

### Manage environment with makefile
If you feel uncomfortable using make file, you can see [here](makefile) the specific commands to start the environment using docker-compose.

 * Build the environment
```
make build
```
 * Start the environment (exclude jenkins slave)
```
make run
```
 * Stop the environment
```
make stop
```
 * Clean environment (Use carefully, this command will delete container volumes too)
```
make clean-data
```
 * Clean images
```
make clean-images
```
 * Tail jenkins logs
```
make jenkins-log
```

***_NOTE:_*** For installing the components separately, you can use [docker-compose.yml](docker-compose.yml) file to see what each of the components need to start in a container.

### Post-installation configurations for a complete CI flow
 * [Setup Plugins](jenkins-pipeline/configure-plugins.md)
 * [Setup Jenkins](jenkins-master/configure-jenkins.md)
 * [Setup Bitbucket Webhook](jenkins-pipeline/configure-bitbucket-webhook.md)
 * [Setup Docker Agents](jenkins-master/configure-docker-agents.md)
 * [Setup Pipeline Job](jenkins-pipeline/pipeline-job.md)

### References
   - https://engineering.riotgames.com/news/putting-jenkins-docker-container
   - https://engineering.riotgames.com/news/docker-jenkins-data-persists
   - https://engineering.riotgames.com/news/jenkins-docker-proxies-and-compose
   - https://engineering.riotgames.com/news/jenkins-ephemeral-docker-tutorial
