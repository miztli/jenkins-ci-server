FROM centos:centos7
LABEL maintainer="dark_coneja@hotmail.com"

# Use yum to install NGINX
RUN yum -y update; yum clean all
RUN yum -y install http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm; yum -y makecache
RUN yum -y install nginx-1.10.1

# Cleanup the default NGINX configuration file we dont need
RUN rm /etc/nginx/conf.d/default.conf

# Add our configuration files (we still need to make these)
COPY conf/jenkins.conf /etc/nginx/conf.d/jenkins.conf
COPY conf/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

#Finish up by making sure NGINX is started
CMD ["nginx"]
