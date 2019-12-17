FROM openjdk:8u232

# copy the server JAR into the container
COPY grpc-server.jar /opt/grpc-server/

# expose port
EXPOSE 8090

# Run the server
CMD ["java", "-jar", "/opt/grpc-server/grpc-server.jar"]
