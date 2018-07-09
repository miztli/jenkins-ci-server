From a Jenkins perspective, nothing changes about making jobs. We want to make sure that the job we create is restricted to the label we set on the Docker image node we configured: testslave.

 - On the Jenkins landing page and click New Item
 - For Item name enter “testjob”
 - Select Freestyle project
 - Click OK
 - Make sure Restrict where this project can be run is checked
 - Enter “testslave” in the Label Expression
 - Scroll down and select Execute shell from the Add build step drop down
 - In the Execute Shell command box, enter: ‘ echo "Hello World! This is baby’s first ephemeral build node!" && Sleep 1’
 - Click Save

You’ll now be on your new jobs landing page. Why do I have you add Sleep 1? Fun fact: the Docker Attach provisioner is so fast that if your build job takes under a second to run I’ve found that it has trouble cleaning up the the build slave, so making it take just a little bit of time is useful to making sure you don’t have slaves that don’t clean up.

You’re now ready for the moment of truth.

 * Click on Build Now

The testjob will enter the build queue and you might see “waiting on available executors” while Jenkins goes about provisioning a new build slave. Depending on your system, this can happen very fast - so fast, in fact, that it might provision, run your simple echo command, and exit before you’ve had time watch it. Or it may take 20-30 seconds. I can tell you from personal experience, this is a bit like watching a kettle of water boil.

If something has gone wrong, the job will hang in the build queue, waiting on a node to provision. Debugging this and other pro-tips could really be another entire blog post, but here are a few pointers:

Make sure there are no typos in the label chosen, either in the Jenkins master configuration or on the job configuration itself. Jenkins should confirm that it found testslave in one Cloud provisioner.

Double, triple, quadruple check that when configuring the Docker host a positive response comes back from Test Connection.

Make sure the image name entered in the jenkins configuration matches the name of the image you see when you run Docker images in your command line for your build slave (it should be jenkins_slave).

Make sure you set the network mode in the Create Container Settings section. It should be jenkins_jenkins-net.

Make sure you chose Different Jenkins URL under Launch Mode and that it is set to: http://jenkins_master_1:8080/ (the forward slashes do matter!).

For deeper analysis you can always go to the jenkins logs and see what the plugin is complaining about. Jenkins Landing Page -> Manage Jenkins -> System Log -> All Jenkins Logs. A lot can be derived from the errors it generates. You can also check logs on the system at the command line as well, I left a handy make jenkins-log shortcut to tail the jenkins logs. Or look for the jenkins_slave containers being spun up in docker ps and run a docker logs command on them to see why they can’t start.

However, everything should work - if it does, fire up that victory dance.

###References

 - https://engineering.riotgames.com/news/jenkins-ephemeral-docker-tutorial

