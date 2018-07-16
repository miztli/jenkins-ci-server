# Configure Docker Agents
An agent is typically a machine, or container, which connects to a Jenkins master and executes tasks when directed by the master.
From: [Jenkins Docs](https://jenkins.io/doc/book/glossary/#agent)

After [Setting up Jenkins](configure-jenkins.md), follow the next steps to configure new agents.

 1. Go to ***Jenkins > Manage Jenkins > Configure System > Cloud > Docker Agent templates***.
 2. Type the _Label_ that will be used to match the agents of your Jenkinsfile.
 3. Select the _Enabled_ option.
 4. Type the name followed by the version of the image that will be initiated by a docker container managed by the Jenkins Master, ex: `jenkins-slave:1.0`.
 5. Press on ***Volumes*** button, and add the necessary volumes in order to save all the work done by the containers during the pipelines, ex:
 ```
 jenkins_jenkins-slave-home:/home/jenkins:rw
 /var/run/docker.sock:/var/run/docker.sock:rw
 /home/ubuntu/.ssh:/home/jenkins/.ssh:rw
  ```
 ***_Note:_*** Look at the importance of mounting the ***/var/run/docker.sock*** volume. It's necessary to mount the socket that communicates with the docker deamon to the slave containers, due to the fact that inside the slaves, we'll have the need to build images or use some docker commands. Otherwise, we could have difficulties running docker-in-docker as [this](http://jpetazzo.github.io/2015/09/03/do-not-use-docker-in-docker-for-ci/) article exposes.

 6. On _Remote Filing System Root_ type: ```/home/jenkins```.
 7. On _Usage_ select, 'Only build jobs with label expressions matching this node'.
 8. On _Idle timeout_, type: 10
 9. On _Connect method_ select: 'Attach Docker container'. A new option will pop-up, then type _User:_ Jenkins
 10. On _Pull strategy_ select: 'Never pull'
 11. On _Pull timeout_, type: 300
 12. Finally, click on ***Apply*** and then ***Save*** your job.
