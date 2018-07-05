# CI SERVER with Jenkins + NGINX + Docker
Create a continuous integration server with Jenkins, NGINX and Docker

## Getting Started
These instructions will help you to set up a continuous integration server using Jenkins on top of Docker fronted by an NGINX web proxy to build you CI-CD pipelines.

### Prerequisites
The following software must be installed in your server or local computer

```
Docker v18.04.0-ce
```

### Install

 * Create a bridge network so that containers can see each other.
```
docker network create --driver bridge jenkins-net
```

 * Verify network waas created successfully
```
docker network ls
``` 
 
 * Follow each README.md file inside the directories in the following order:
   - jenkins-master
   - NGINX-server

### References
   - https://engineering.riotgames.com/news/putting-jenkins-docker-container
   - https://engineering.riotgames.com/news/docker-jenkins-data-persists
   - https://engineering.riotgames.com/news/jenkins-docker-proxies-and-compose
