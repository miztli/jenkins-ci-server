FROM centos:centos7
LABEL maintainer="yourname@somewhere.com"

#socat is a linux utility for transporting data between two byte streams
RUN yum -y install socat && \
   yum clean all

#Put our docker.sock from our desktop on one end and TCP port 2375 on the other.
#This is why the docker images is mounting a volume that contains the socket file,
#bridging desktop with docker network..
VOLUME /var/run/docker.sock

# docker tcp port
EXPOSE 2375

ENTRYPOINT ["socat", "TCP-LISTEN:2375,reuseaddr,fork","UNIX-CLIENT:/var/run/docker.sock"]
