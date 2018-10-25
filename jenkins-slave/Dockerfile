### Image for building a groovy slave
FROM centos:centos7
LABEL maintainer="dark_coneja@hotmail.com"

# Install Essentials
##RUN yum update -y && \
##    yum clean all

# Install docker
RUN yum install -y \
    yum-utils \
    device-mapper-persistent-data \
    lvm2

# Setup docker stable repository
RUN yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

# Enable edge repository
RUN yum-config-manager --enable docker-ce-edge

# Install Packages
RUN yum makecache \
    && yum update -y \
    && yum install -y \
    wget \
    git \
    java-1.8.0-openjdk-devel \
    docker-ce-18.04.0.ce-3.el7.centos \
	  sudo \
	  make \
    gcc-c++ \
    bzip2 \
    bzip2-libs \
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

# Add jenkins to docker group
RUN usermod -aG docker ${user}
# Add jenkis to input group WTF??? <---- CHECK
RUN usermod -aG input ${user}

# Add the jenkins user to sudoers
RUN echo "${user}    ALL=(ALL)    ALL" >> etc/sudoers

# Jenkins home directory is a volume, so configuration and build history
# can be persisted and survive image upgrades
VOLUME /home/jenkins

# Set Name Servers
COPY /files/resolv.conf /etc/resolv.conf
