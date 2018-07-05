# NGINX proxy server
Create an NGINX proxy server to front our jenkins master server.

## Getting Started
These instructions will help you to set up an NGINX proxy server.

### Prerequisites
The following software must be installed in your server or local computer

```
Docker v18.04.0-ce
```

### Install

 * Create the docker image
```
docker build -t jenkins-nginx:0.1 . 
```

### References
   - https://engineering.riotgames.com/news/putting-jenkins-docker-container
   - https://engineering.riotgames.com/news/docker-jenkins-data-persists
   - https://engineering.riotgames.com/news/jenkins-docker-proxies-and-compose
