# Plugins Setup

The following plugins must be installed in orther to have CI example completely up and running.

 - JDK Plugin
 - Gradle Plugin (For gradle builds)
 - Blue Ocean Plugin ('Optional' for better UI traceability)
 - Pipeline Plugin (For jenkinsfile and job configuration)
 - Bitbucket Plugin (For Bitbucket SCM integration)
 - Docker Plugin (For docker build slaves)
 - Amazon ECR Plugin (For aws integration)

### Plugin configuration
Be sure you have installed all previous plugins from the list.

##### JDK Plugin
 1. Go to ***Manage Jenkins > Global Tool Configuration > JDK > JDK Installations > Add JDK***
 2. Type a Name for the JDK installation.
 3. Select install automatically, or type the Path to your JDK executable.

##### Gradle Plugin
 1. Go to ***Manage Jenkins > Global Tool Configuration > Gradle > Gradle Installations > Add Gradle***
 2. Type a Name for the Gradle installation.
 3. Select install automatically, or type the Path to your Gradle executable.

##### Docker Plugin (For docker build slaves)
 1. Go to ***Manage Jenkins > Global Tool Configuration > Docker > Docker Installations > Add Docker***
 2. Type a Name for the Docker installation.
 3. Select install automatically, or type the Path to your Docker executable.


