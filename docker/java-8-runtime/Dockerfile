FROM openjdk:8u232

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

# Set up JAR volume
VOLUME /opt/java/jar

# CD into JAR volume
WORKDIR /opt/java/jar

# Run the server
ENTRYPOINT ["entrypoint.sh"]
