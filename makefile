build: #start all the environment
	@docker-compose -p jenkins build
run: #exclude jenkins-slave
	@docker-compose -p jenkins up -d nginx master proxy
stop: #stop the environment
	@docker-compose -p jenkins down
clean-data: #BEWARE!!!, volumes will also be removed
	@docker-compose -p jenkins down -v
clean-images: #clean images which are not used by a container
	@docker rmi `docker images -q -f "dangling=true"`
jenkins-log: #tail jenkins log
	@docker-compose -p jenkins exec master tail -f /var/log/jenkins/jenkins.log
