FROM jenkins/jenkins:2.130-alpine
LABEL mantainer="dark_coneja@hotmail.com"

USER root
RUN mkdir /var/log/jenkins
RUN chown -R jenkins:jenkins /var/log/jenkins
RUN mkdir /var/cache/jenkins
RUN chown -R jenkins:jenkins /var/cache/jenkins

USER jenkins

#8GB memory pool to handle garbage collection
ENV JAVA_OPTS="-Xmx8192"

#Move uncompressed Jenkins war file from JENKINS_HOME, set webroot variable to avoid storing it and savig these data
ENV JENKINS_OPTS="--handlerCountMax=300 --logfile=/var/log/jenkins/jenkins.log --webroot=/var/cache/jenkins/war"
