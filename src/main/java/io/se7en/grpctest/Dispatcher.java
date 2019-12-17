package io.se7en.grpctest;

import coprocess.CoprocessObject.Object;
import coprocess.DispatcherGrpc.DispatcherImplBase;
import io.grpc.stub.StreamObserver;

public final class Dispatcher extends DispatcherImplBase {
  @Override
  public void dispatch(Object request, StreamObserver<Object> responseObserver) {

  }
}
