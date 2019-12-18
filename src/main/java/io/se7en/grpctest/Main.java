package io.se7en.grpctest;

import java.io.IOException;
import java.util.Properties;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public final class Main {
  private static final Properties PROPERTIES = loadProperties();

  private static Properties loadProperties() {
    Properties props = new Properties();
    props.putAll(System.getProperties());
    return props;
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    Server server = buildServer(port());
    System.out.println("Server started!");

    server.awaitTermination();
  }

  private static int port() {
    int port = Integer.parseInt(PROPERTIES.getProperty("port", "8090"));
    System.out.println("Using port " + port + "");
    return port;
  }

  private static Server buildServer(int port) throws IOException {
    return ServerBuilder.forPort(port).addService(new Dispatcher()).build().start();
  }
}
