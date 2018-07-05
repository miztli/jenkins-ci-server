# jenkins-ci-server
Create a Jenkins server with docker

## Getting Started
These instructions will help you to set up a Jenkins server on top of docker to build you CI-CD pipelines.

### Prerequisites
The following software must be installed in your server or local computer

```
Docker v18.04.0-ce
```

### Install

 * Build and tag Dockerfile to create the Docker image
```
docker build -t jenkins-server:0.1 .
```

 * Create 2 docker data volumes: One for jenkins' logs and the other for jenkins' data

```
docker volume create jenkins-logs
docker volume create jenkins-data
```

 * Verify that volumes were created
```
docker volume ls
```

 * Start the container
```
docker run -p 8080:8080 -p 50000:50000 --name=jenkins-master --mount source=jenkins-log,target=/var/log/jenkins --mount source=jenkins-data,target=/var/jenkins_home -d jenkins-server:0.1
```

 * Verify that container is up and running
```
docker ps
```

 * Verify that mount points are working as expected
```
docker exec jenkins-master ls /var/cache/jenkins/war
docker exec jenkins-master tail -f /var/log/jenkins/jenkins.log
```
 
 * If you lost the initial password, execute:
```
docker exec jenkins-master cat /var/jenkins_home/secrets/initialAdminPassword
``` 

### Create a jenkins pipeline

 * Install jenkins Pipeline plugin from Manage Plugins

### References
   - https://engineering.riotgames.com/news/putting-jenkins-docker-container
   - https://engineering.riotgames.com/news/docker-jenkins-data-persists
