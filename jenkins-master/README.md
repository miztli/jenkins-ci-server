# jenkins-ci-server
Create a Jenkins server with docker

## Getting Started
For starting the complete environment, run the docker-compose.yml file outside this directory.
These instructions will help you to set up a Jenkins master server on top of docker to build you CI-CD pipelines.

### Prerequisites
The following software must be installed in your server or local computer

```
Docker v18.04.0-ce
```

### Install

 * Build and tag Dockerfile to create the Docker image
```
docker build -t jenkins-master:0.1 .
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

 * Start the container (standalone)
```
docker run -p 8080:8080 -p 50000:50000 --name=jenkins-master --network=jenkins-net --mount source=jenkins-log,target=/var/log/jenkins --mount source=jenkins-data,target=/var/jenkins_home -d jenkins-master:0.1
```

* Start the container (fronted by an NGINX server, no longer need to expose port 8080)
```                                                                                                             
docker run -p 50000:50000 --name=jenkins-master --network=jenkins-net --mount source=jenkins-log,target=/var/log/jenkins --mount source=jenkins-data,target=/var/jenkins_home -d jenkins-master:0.1                
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
