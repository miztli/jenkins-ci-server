FROM centos:centos7
LABEL maintainer="dark_coneja@hotmail.com"

# Install Essentials
##RUN yum update -y && \
##    yum clean all

# Install Packages
RUN yum makecache \
    && yum update -y \
    && yum install -y \
    wget \
    git \
    java-1.8.0-openjdk \
	  sudo \
	  && yum clean all

ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000

ENV JENKINS_HOME /home/${user}
ENV JAVA_HOME "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.171-8.b10.el7_5.x86_64"

# Jenkins is run with user `jenkins`, uid = 1000
RUN groupadd -g ${gid} ${group} \
    && useradd -d "$JENKINS_HOME" -u ${uid} -g ${gid} -m -s /bin/bash ${user}

RUN chown -R ${user}:${user} /home/${user}

# Add the jenkins user to sudoers
RUN echo "${user}    ALL=(ALL)    ALL" >> etc/sudoers

# Set Name Servers
COPY /files/resolv.conf /etc/resolv.conf

ENTRYPOINT ["bash"]
