package io.se7en.grpctest;

import java.util.function.UnaryOperator;

import coprocess.CoprocessObject.Object;
import coprocess.CoprocessObject.Object.Builder;
import coprocess.CoprocessReturnOverrides.ReturnOverrides;

public class PreHook implements UnaryOperator<Object> {
  private static final String SECRET_HEADER_NAME = "X-Secret-Header";
  private static final String CORRECT_SECRET_HEADER = "grpcTest";

  @Override
  public Object apply(Object request) {
    return hasCorrectSecretHeader(request) ? transform(request) : imATeapot(request);
  }

  private boolean hasCorrectSecretHeader(Object request) {
    return request.getRequest().getHeadersOrDefault(SECRET_HEADER_NAME, "").equals(CORRECT_SECRET_HEADER);
  }

  private Object transform(Object request) {
    Builder builder = request.toBuilder();

    builder.getRequestBuilder().addDeleteHeaders(SECRET_HEADER_NAME).setUrl("/anything");

    return builder.build();
  }

  private Object imATeapot(Object request) {
    Builder builder = request.toBuilder();

    builder
      .getRequestBuilder()
      .setReturnOverrides(ReturnOverrides.newBuilder().setResponseCode(418).setResponseError("I'm a teapot!").build());

    return builder.build();
  }
}
