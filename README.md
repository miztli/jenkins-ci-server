# CI SERVER with Jenkins + NGINX + Docker
Create a continuous integration server farm with Jenkins, NGINX and Docker

## Getting Started
These instructions will help you to set up a continuous integration server using Jenkins on top of Docker fronted by an NGINX web proxy to build you CI-CD pipelines.

### Prerequisites
The following software must be installed on your server or local computer

```
Docker v18.04.0-ce
Docker compose 1.19.0
```

### Install (Standalone mode)

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
   - nginx-server

### Install complete environment with docker compose

 * Build images
```
docker-compose build
```

 * Start containers
```
docker-compose -p jenkins up -d
```

 * Verify deployed containers
```
docker-compose -p jenkins ps
```

 * Feel free to verify what docker compose has created
```
docker network ls
docker volume ls
```

 * Stop the jenkis environment
```
docker-compose -p jenkins down
```

### Manage environment with makefile
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
