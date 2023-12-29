# step 1:
ARG JENKINS_IMAGE=docker.io/jenkins/jenkins
ARG JENKINS_TAG=lts
FROM "${JENKINS_IMAGE}:${JENKINS_TAG}"
COPY plugins.txt plugins.txt
RUN jenkins-plugin-cli --plugin-file plugins.txt

# step 2:
USER root
RUN rm /bin/sh && ln -s /bin/bash /bin/sh

RUN curl -fsSL https://get.docker.com | sh
RUN sed -i "s/999/99/" /etc/group

RUN apt-get update && \
    apt-get install -y unzip zip && \
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
    unzip awscliv2.zip && \
    ./aws/install

# step 3:
RUN usermod -aG docker jenkins
    
# step 4:
ARG HOST_DOCKER_GID
RUN groupmod -g "$HOST_DOCKER_GID" docker
# step 5:
USER jenkins  

# Install SDKMan
RUN curl -s "https://get.sdkman.io" | bash && \
    source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    sdk install java 8.0.392-amzn

# /var/jenkins_home/.sdkman/candidates/java/8.0.392-amzn