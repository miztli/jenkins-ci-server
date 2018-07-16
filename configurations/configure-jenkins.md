# Configure Jenkins
Before we continue, I want to cover some quick Jenkins basics. Jenkins has a concept of a “build node” and a “job.” Build nodes have N number of executors. When Jenkins wants to run a job, it tries to find a spare executor (on a build node) to run that job. A default Jenkins installation will try to find any spare executor to run on. While simple, most Jenkins installs modify this because they want certain jobs to run on certain kinds of build nodes (imagine wanting a Windows operating system versus a Linux operating system). To do that, “labels” are applied to build nodes, and jobs are then restricted to only run on build nodes with matching labels.

For our ephemeral Docker slaves, we’re going to leverage this label capability to tie Docker images and jobs to those labels. Thanks to the excellent design of the JClouds plugin and Docker Plugin, Jenkins will check the label of a job when it enters the queue. The plugin will ascertain that there are no available executors for the job, and therefore attempt to create a build node with an appropriate Docker image. The new node will be given the correct label, and the queued build job will run.

It is, in effect, almost like magic! This behavior is the key to how ephemeral nodes work.

We’re now ready to configure our Dockerhost and first ephemeral slave on Jenkins. On the Jenkins landing page do the following:

 - Click on Manage Jenkins

 - Click on Configure System

 - Scroll down until you find Add new cloud as a drop down (this comes from the Jclouds plugin)

 - Select Docker from the drop down

A new form pops. This form is the high level information you need to enter about your Dockerhost. Please note, you can create many Dockerhosts if you want. This is one way you can manage which build images run on which machines. For this tutorial, we’ll stick with one.

 * In the Name field enter “LocalDockerHost”

 * In the Docker Host URI field enter: “tcp://proxy1:2375”

Note WINDOWS USERS ONLY: You aren’t using a proxy container, so just set this to: tcp://docker.for.win.localhost:2375 which happens to be a prenamed DNS entry for your convenience.

***Click on Test Connection and you should get a response that shows Version and API version of your docker host.***

If no response comes back or you get error text, something has gone wrong and Jenkins cannot talk to your Dockerhost. I’ve done my best to make sure this walkthrough “just works” but here’s a quick list of things that could be broken and affect this:

 - Your docker-compose file has a typo in it. Verify that there is an Alias for the proxy container set to “proxy1.”

 - Your docker proxy didn’t start for some reason. Check docker ps and verify you have a proxy container running.

 - For some reason your docker.sock file is not at /var/run/docker.sock. This tutorial assumes a default installation of Docker for Mac/Win. If you’ve reconfigured it then this won’t work.

Assuming you got a successful version response when testing the connection, we can proceed. We want to add our freshly minted build slave image as a potential build node candidate.

 - Click the Enabled checkbox (by default this is off, this is a convenient way to disable a cloud provider for maintenance/etc in jenkins)

 - Click on the Docker Agent templates... button

 - Click on Add Docker Template button

 - In the Labels field enter testslave

 - Make sure the Enabled checkbox is selected (you can use this to disable specific images if they are causing problems)

 - For the Docker Image field enter: jenkins-slave:0.1

 - For persistent data, add the necessary volumes, ex: `/home/jenkins:/home/`

 - For Remote Filing System Root enter /home/jenkins (this will be where the jenkins workspace goes in the container)

 - For Usage select only build jobs with the label expressions matching this node

 - Make sure the Connect Method is set to Attach Docker Container (this is the default and allows jenkins to attach/exec into the container, start the jenkins slave agent and point it back to your Jenkins server)

 - For User enter jenkins

  - Change pull strategy to Never pull (we make this image by building it, not pulling it from a repo)

Click Save at the bottom of the configuration page

We have one last thing to do now, and that’s make a build job to test this setup and confirm everything works.

### Start your first job
Click [here](configure-first-job.md) to follow the configuration steps for your first job


### References

 - https://engineering.riotgames.com/news/jenkins-ephemeral-docker-tutorial
