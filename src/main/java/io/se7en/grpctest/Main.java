package io.se7en.grpctest;

import java.io.IOException;
import java.util.Properties;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Main {
  private static final Properties PROPERTIES = loadProperties();

  private static Properties loadProperties() {
    Properties props = new Properties();
    props.putAll(System.getProperties());
    return props;
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    int port = port();

    System.out.println("Starting server at port " + port + "...");
    Server server = ServerBuilder.forPort(port).build().start();
    System.out.println("Server started!");

    server.awaitTermination();
  }

  private static int port() {
    return Integer.parseInt(PROPERTIES.getProperty("port", "8090"));
  }
}
