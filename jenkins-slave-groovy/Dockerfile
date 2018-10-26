## Description: Jenkins slave image.
## JAVA 8 + Alpine linux 3.8 + Groovy 2.5.3
FROM openjdk:8-jdk-alpine

MAINTAINER Miztli Melgoza

# ------------------------------------------
# Install latest version of git and openssh
RUN apk update && \
    apk upgrade && \
    apk add --no-cache git \
    openssh
# ------------------------------------------

# ------------------------------------------
# Install groovy. 
# Copied from: https://github.com/groovy/docker-groovy/blob/1b26ed9353728e24805cc5959b71d2661c6de5d1/jdk8-alpine/Dockerfile
# Couldn't use directly groovy image because I needed to change grovy's user uid and group id, 
# so that they do not interfere with jenkins
CMD ["groovysh"]

ENV GROOVY_PATH /opt/groovy
ENV GROOVY_HOME_DIR /home/groovy
ENV GROOVY_GROUP groovy
ENV GROOVY_GROUP_ID 1001
ENV GROOVY_USER groovy
ENV GROOVY_USER_ID 1001
ENV GROOVY_VERSION 2.5.3

RUN set -o errexit -o nounset \
    && echo "Installing build dependencies" \
    && apk add --no-cache --virtual .build-deps \
        gnupg \
    \
    && echo "Downloading Groovy" \
    && wget -qO groovy.zip "https://dist.apache.org/repos/dist/release/groovy/${GROOVY_VERSION}/distribution/apache-groovy-binary-${GROOVY_VERSION}.zip" \
    \
    && echo "Importing keys listed in http://www.apache.org/dist/groovy/KEYS from key server" \
    && export GNUPGHOME="$(mktemp -d)"; \
    for key in \
        "7FAA0F2206DE228F0DB01AD741321490758AAD6F" \
        "331224E1D7BE883D16E8A685825C06C827AF6B66" \
        "34441E504A937F43EB0DAEF96A65176A0FB1CD0B" \
        "9A810E3B766E089FFB27C70F11B595CEDC4AEBB5" \
        "81CABC23EECA0790E8989B361FF96E10F0E13706" \
    ; do \
        for server in \
            "ha.pool.sks-keyservers.net" \
            "hkp://p80.pool.sks-keyservers.net:80" \
            "pgp.mit.edu" \
        ; do \
            echo "  Trying ${server}"; \
            if gpg --keyserver "${server}" --recv-keys "${key}"; then \
                break; \
            fi; \
        done; \
    done; \
    if [ $(gpg --list-keys | grep -c "pub ") -ne 5 ]; then \
        echo "ERROR: Failed to fetch GPG keys" >&2; \
        exit 1; \
    fi \
    \
    && echo "Checking download signature" \
    && wget -qO groovy.zip.asc "https://dist.apache.org/repos/dist/release/groovy/${GROOVY_VERSION}/distribution/apache-groovy-binary-${GROOVY_VERSION}.zip.asc" \
    && gpg --batch --verify groovy.zip.asc groovy.zip \
    && rm -rf "${GNUPGHOME}" \
    && rm groovy.zip.asc \
    \
    && echo "Installing Groovy" \
    && unzip groovy.zip \
    && rm groovy.zip \
    && mkdir /opt \
    && mv "groovy-${GROOVY_VERSION}" "${GROOVY_PATH}/" \
    && ln -s "${GROOVY_PATH}/bin/grape" /usr/bin/grape \
    && ln -s "${GROOVY_PATH}/bin/groovy" /usr/bin/groovy \
    && ln -s "${GROOVY_PATH}/bin/groovyc" /usr/bin/groovyc \
    && ln -s "${GROOVY_PATH}/bin/groovyConsole" /usr/bin/groovyConsole \
    && ln -s "${GROOVY_PATH}/bin/groovydoc" /usr/bin/groovydoc \
    && ln -s "${GROOVY_PATH}/bin/groovysh" /usr/bin/groovysh \
    && ln -s "${GROOVY_PATH}/bin/java2groovy" /usr/bin/java2groovy \
    \
    && echo "Cleaning up build dependencies" \
    && apk del .build-deps \
    \
    && echo "Adding groovy user and group" \
    && addgroup -S -g ${GROOVY_GROUP_ID} ${GROOVY_GROUP} \
    && adduser -D -S -G ${GROOVY_GROUP} -u ${GROOVY_USER_ID} -s /bin/ash ${GROOVY_USER} \
    && mkdir -p "${GROOVY_HOME_DIR}/.groovy/grapes" \
    && chown -R ${GROOVY_USER}:${GROOVY_GROUP} ${GROOVY_HOME_DIR} \
    \
    && echo "Symlinking root .groovy to groovy .groovy" \
    && ln -s "${GROOVY_HOME_DIR}/.groovy" /root/.groovy

# Create Grapes volume
# USER groovy
VOLUME "${GROOVY_HOME_DIR}/.groovy/grapes"
# WORKDIR ${GROOVY_HOME_DIR}

# Test groovy instalation
RUN set -o errexit -o nounset \
    && echo "Testing Groovy installation" \
    && groovy --version

## End groovy installation
# ----------------------------------------------------

# ----------------------------------------------------
# Install gradle
# Copied from: https://github.com/keeganwitt/docker-gradle/blob/1718fa65873e24d1e003dcd3828755e9fd39fa9d/jdk8-slim/Dockerfile
CMD ["gradle"]

ENV GRADLE_HOME /opt/gradle
ENV GRADLE_USER gradle
ENV GRADLE_USER_ID 1002
ENV GRADLE_GROUP gradle
ENV GRADLE_GROUP_ID 1002
ENV GRADLE_VERSION 4.10.2

ARG GRADLE_DOWNLOAD_SHA256=b49c6da1b2cb67a0caf6c7480630b51c70a11ca2016ff2f555eaeda863143a29
RUN set -o errexit -o nounset \
	&& echo "Downloading Gradle" \
	&& wget -O gradle.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
	\
	&& echo "Checking download hash" \
	&& echo "${GRADLE_DOWNLOAD_SHA256} *gradle.zip" | sha256sum -c - \
	\
	&& echo "Installing Gradle" \
	&& unzip gradle.zip \
	&& rm gradle.zip \
	&& mv "gradle-${GRADLE_VERSION}" "${GRADLE_HOME}/" \
	&& ln -s "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle \
	\
	&& echo "Adding gradle user and group" \
	&& addgroup -S -g ${GRADLE_GROUP_ID} ${GRADLE_GROUP} \
	&& adduser -D -S -G ${GRADLE_GROUP} -u ${GRADLE_USER_ID} -s /bin/ash ${GRADLE_USER} \
	&& mkdir "${GRADLE_HOME}/.gradle" \
	&& chown -R ${GRADLE_USER}:${GRADLE_GROUP} ${GRADLE_HOME} \
	\
	&& echo "Symlinking root Gradle cache to gradle Gradle cache" \
	&& ln -s "${GRADLE_HOME}/.gradle" /root/.gradle

# Create Gradle volume
# USER gradle
VOLUME "${GRADLE_HOME}/.gradle"
# WORKDIR /home/gradle

RUN set -o errexit -o nounset \
	&& echo "Testing Gradle installation" \
	&& gradle --version
## End gradle installation
# ----------------------------------------------------

# ----------------------------------------------------
# Install docker CLI only (docker cli version must be same as jenkins master)
ENV DOCKER_CLI_VERSION "18.06.1-ce"
ENV DOWNLOAD_URL "https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKER_CLI_VERSION}.tgz"

RUN apk --update add curl \
    && mkdir -p /tmp/download \
    && curl -L $DOWNLOAD_URL | tar -xz -C /tmp/download \
    && mv /tmp/download/docker/docker /usr/local/bin/ \
    && rm -rf /tmp/download \
    && apk del curl \
    && rm -rf /var/cache/apk/*
# End docker cli installation
# ----------------------------------------------------

# ----------------------------------------------------
# Configure jenkins workspace
# Add jenkins group and user
ENV JENKINS_USER jenkins
ENV JENKINS_USER_UID 1000
ENV JENKINS_GROUP jenkins
ENV JENKINS_GROUP_ID 1000
ENV JENKINS_HOME "/home/${JENKINS_USER}"

# Jenkins is run with user `jenkins`, uid = 1000
# If you bind mount a volume from the host or a data container, ensure you use the same uid
RUN addgroup -S -g ${JENKINS_GROUP_ID} ${JENKINS_GROUP} \
    && adduser -D -S -G ${JENKINS_GROUP} -u ${JENKINS_USER_UID} -s /bin/ash ${JENKINS_USER} \
    && chown -R ${JENKINS_USER}:${JENKINS_GROUP} ${GROOVY_HOME_DIR}

# Jenkins home directory is a volume, so configuration and build history
# can be persisted and survive image upgrades
VOLUME $JENKINS_HOME

# Change to jenkins user
USER ${JENKINS_USER}
# End jenkins installation
# ----------------------------------------------------

CMD ["/bin/ash"]