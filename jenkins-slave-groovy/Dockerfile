## Description: Jenkins slave image.
## JAVA 8 + Alpine linux 3.8 + Groovy 2.5.3 + Gradle 4.10.2
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
# Copied from: https://github.com/keeganwitt/docker-gradle/blob/1718fa65873e24d1e003dcd3828755e9fd39fa9d/jdk8-alpine/Dockerfile
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

# ------------------------------------------
# Install python (for az-cli)
# Copied from: https://github.com/docker-library/python/blob/ac49e0bb09aafe4100fe5662636c24fce7206008/2.7/alpine3.8/Dockerfile
# http://bugs.python.org/issue19846
# > At the moment, setting "LANG=C" on a Linux system *fundamentally breaks Python 3*, and that's not OK.
ENV LANG C.UTF-8
# https://github.com/docker-library/python/issues/147
ENV PYTHONIOENCODING UTF-8

# install ca-certificates so that HTTPS works consistently
# other runtime dependencies for Python are installed later
RUN apk add --no-cache ca-certificates

ENV GPG_KEY C01E1CAD5EA2C4F0B8E3571504C367C218ADD4FF
ENV PYTHON_VERSION 2.7.15

RUN set -ex \
	&& apk add --no-cache --virtual .fetch-deps \
		gnupg \
		tar \
		xz \
	\
	&& wget -O python.tar.xz "https://www.python.org/ftp/python/${PYTHON_VERSION%%[a-z]*}/Python-$PYTHON_VERSION.tar.xz" \
	&& wget -O python.tar.xz.asc "https://www.python.org/ftp/python/${PYTHON_VERSION%%[a-z]*}/Python-$PYTHON_VERSION.tar.xz.asc" \
	&& export GNUPGHOME="$(mktemp -d)" \
	&& gpg --keyserver ha.pool.sks-keyservers.net --recv-keys "$GPG_KEY" \
	&& gpg --batch --verify python.tar.xz.asc python.tar.xz \
	&& { command -v gpgconf > /dev/null && gpgconf --kill all || :; } \
	&& rm -rf "$GNUPGHOME" python.tar.xz.asc \
	&& mkdir -p /usr/src/python \
	&& tar -xJC /usr/src/python --strip-components=1 -f python.tar.xz \
	&& rm python.tar.xz \
	\
	&& apk add --no-cache --virtual .build-deps  \
		bzip2-dev \
		coreutils \
		dpkg-dev dpkg \
		findutils \
		gcc \
		gdbm-dev \
		libc-dev \
		libnsl-dev \
		libressl-dev \
		libtirpc-dev \
		linux-headers \
		make \
		ncurses-dev \
		pax-utils \
		readline-dev \
		sqlite-dev \
		tcl-dev \
		tk \
		tk-dev \
		zlib-dev \
# add build deps before removing fetch deps in case there's overlap
	&& apk del .fetch-deps \
	\
	&& cd /usr/src/python \
	&& gnuArch="$(dpkg-architecture --query DEB_BUILD_GNU_TYPE)" \
	&& ./configure \
		--build="$gnuArch" \
		--enable-shared \
		--enable-unicode=ucs4 \
	&& make -j "$(nproc)" \
# set thread stack size to 1MB so we don't segfault before we hit sys.getrecursionlimit()
# https://github.com/alpinelinux/aports/commit/2026e1259422d4e0cf92391ca2d3844356c649d0
		EXTRA_CFLAGS="-DTHREAD_STACK_SIZE=0x100000" \
	&& make install \
	\
	&& find /usr/local -type f -executable -not \( -name '*tkinter*' \) -exec scanelf --needed --nobanner --format '%n#p' '{}' ';' \
		| tr ',' '\n' \
		| sort -u \
		| awk 'system("[ -e /usr/local/lib/" $1 " ]") == 0 { next } { print "so:" $1 }' \
		| xargs -rt apk add --no-cache --virtual .python-rundeps \
	&& apk del .build-deps \
	\
	&& find /usr/local -depth \
		\( \
			\( -type d -a \( -name test -o -name tests \) \) \
			-o \
			\( -type f -a \( -name '*.pyc' -o -name '*.pyo' \) \) \
		\) -exec rm -rf '{}' + \
	&& rm -rf /usr/src/python \
	\
	&& python2 --version

# if this is called "PIP_VERSION", pip explodes with "ValueError: invalid truth value '<VERSION>'"
ENV PYTHON_PIP_VERSION 18.1

RUN set -ex; \
	\
	wget -O get-pip.py 'https://bootstrap.pypa.io/get-pip.py'; \
	\
	python get-pip.py \
		--disable-pip-version-check \
		--no-cache-dir \
		"pip==$PYTHON_PIP_VERSION" \
	; \
	pip --version; \
	\
	find /usr/local -depth \
		\( \
			\( -type d -a \( -name test -o -name tests \) \) \
			-o \
			\( -type f -a \( -name '*.pyc' -o -name '*.pyo' \) \) \
		\) -exec rm -rf '{}' +; \
	rm -f get-pip.py
# End python installation
# ------------------------------------------

# ----------------------------------------------------
# Azure cli
ARG CLI_VERSION

# Metadata as defined at http://label-schema.org
ARG BUILD_DATE

ENV JP_VERSION="0.1.3"

LABEL maintainer="Microsoft" \
      org.label-schema.schema-version="1.0" \
      org.label-schema.vendor="Microsoft" \
      org.label-schema.name="Azure CLI" \
      org.label-schema.version=$CLI_VERSION \
      org.label-schema.license="MIT" \
      org.label-schema.description="The Azure CLI is used for all Resource Manager deployments in Azure." \
      org.label-schema.url="https://docs.microsoft.com/en-us/cli/azure/overview" \
      org.label-schema.usage="https://docs.microsoft.com/en-us/cli/azure/install-az-cli2#docker" \
      org.label-schema.build-date=$BUILD_DATE \
      org.label-schema.vcs-url="https://github.com/Azure/azure-cli.git" \
      org.label-schema.docker.cmd="docker run -v \${HOME}/.azure:/root/.azure -it microsoft/azure-cli:$CLI_VERSION"

WORKDIR azure-cli
COPY ./azure-cli /azure-cli

# bash gcc make openssl-dev libffi-dev musl-dev - dependencies required for CLI
# openssh - included for ssh-keygen
# ca-certificates

# curl - required for installing jp
# jq - we include jq as a useful tool
# pip wheel - required for CLI packaging
# jmespath-terminal - we include jpterm as a useful tool
# 1. Build packages and store in tmp dir
# 2. Install the cli and the other command modules that weren't included
# 3. Temporary fix - install azure-nspkg to remove import of pkg_resources in azure/__init__.py (to improve performance)
RUN apk add --no-cache bash openssh ca-certificates jq curl openssl git \
 && apk add --no-cache --virtual .build-deps gcc make openssl-dev libffi-dev musl-dev \
 && update-ca-certificates \
 && curl https://github.com/jmespath/jp/releases/download/${JP_VERSION}/jp-linux-amd64 -o /usr/local/bin/jp \
 && chmod +x /usr/local/bin/jp \
 && pip install --no-cache-dir --upgrade jmespath-terminal \
 && /bin/bash -c 'TMP_PKG_DIR=$(mktemp -d); \
    for d in src/azure-cli src/azure-cli-telemetry src/azure-cli-core src/azure-cli-nspkg src/azure-cli-command_modules-nspkg src/command_modules/azure-cli-*/; \
    do cd $d; echo $d; python setup.py bdist_wheel -d $TMP_PKG_DIR; cd -; \
    done; \
    [ -d privates ] && cp privates/*.whl $TMP_PKG_DIR; \
    all_modules=`find $TMP_PKG_DIR -name "*.whl"`; \
    pip install --no-cache-dir $all_modules; \
    pip install --no-cache-dir --force-reinstall --upgrade azure-nspkg azure-mgmt-nspkg;' \
 && cat /azure-cli/az.completion > ~/.bashrc \
 && runDeps="$( \
    scanelf --needed --nobanner --recursive /usr/local \
        | awk '{ gsub(/,/, "\nso:", $2); print "so:" $2 }' \
        | sort -u \
        | xargs -r apk info --installed \
        | sort -u \
    )" \
 && apk add --virtual .rundeps $runDeps \
 && apk del .build-deps 
# End azure cli installation
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

# Install kubectl
# Note: Latest version may be found on:
# https://aur.archlinux.org/packages/kubectl-bin/
ENV KUBECTL_VERSION "https://storage.googleapis.com/kubernetes-release/release/v1.9.6/bin/linux/amd64/kubectl"
ENV KUBECTL_HOME "/config"
ENV KUBECTL_USER "kubectl"
ENV KUBECTL_USER_ID 2342

ADD ${KUBECTL_VERSION} /usr/local/bin/kubectl

RUN set -x && \
    apk add --no-cache curl ca-certificates && \
    chmod +x /usr/local/bin/kubectl && \
    \
    # Create non-root user (with a randomly chosen UID/GUI).
    adduser ${KUBECTL_USER} -Du ${KUBECTL_USER_ID} -h ${KUBECTL_HOME} && \
    \
    # Basic check it works.
    kubectl version --client
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
    && chown -R ${JENKINS_USER}:${JENKINS_GROUP} ${JENKINS_HOME}

# Jenkins home directory is a volume, so configuration and build history
# can be persisted and survive image upgrades
WORKDIR ${JENKINS_HOME}
VOLUME ${JENKINS_HOME}

# Change to jenkins user
USER ${JENKINS_USER}
# End jenkins installation
# ----------------------------------------------------

CMD ["/bin/ash"]