package com.pccw.tyk.grpctest;

import java.util.Optional;
import java.util.function.Function;

import coprocess.CoprocessObject.Object;
import coprocess.DispatcherGrpc.DispatcherImplBase;
import io.grpc.stub.StreamObserver;

public final class Dispatcher extends DispatcherImplBase {
  private static final Function<Object, Optional<Object>> TRANSFORMER = HookRegistry.getTransformer();

  @Override
  public void dispatch(Object request, StreamObserver<Object> responseObserver) {
    printRequest("Request: ", request);

    Optional<Object> newRequest = TRANSFORMER.apply(request);

    responseObserver.onNext(newRequest.orElse(request));
    newRequest.ifPresent(r -> printRequest("Final Request to Upstream: ", r));

    responseObserver.onCompleted();
  }

  private void printRequest(String header, Object request) {
    System.out.println(header);
    System.out.println("===REQUEST START===");
    System.out.println(request);
    System.out.println("====REQUEST END====");
  }
}
