package io.se7en.grpctest;

import java.util.Optional;
import java.util.function.UnaryOperator;

import coprocess.CoprocessObject.Object;
import coprocess.CoprocessObject.Object.Builder;
import coprocess.CoprocessReturnOverrides.ReturnOverrides;

public class PostHook implements UnaryOperator<Object> {
  private static final String STOP_HEADER_NAME = "X-Last-Moment-Stop";

  @Override
  public Object apply(Object request) {
    return Optional.of(request).filter(this::hasStopHeader).map(this::transform).orElse(request);
  }

  private boolean hasStopHeader(Object request) {
    return !request.getRequest().getHeadersOrDefault(STOP_HEADER_NAME, "").isEmpty();
  }

  private Object transform(Object request) {
    Builder builder = request.toBuilder();

    builder
      .getRequestBuilder()
      .setReturnOverrides(
        ReturnOverrides
          .newBuilder()
          .setResponseCode(200)
          .setResponseError(
            "STOP! Message was [" + request.getRequest().getHeadersOrDefault(STOP_HEADER_NAME, "") + "]"
          )
          .build()
      );

    return builder.build();
  }
}
