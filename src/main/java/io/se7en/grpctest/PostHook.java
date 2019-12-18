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
    return getStopHeader(request).map(s -> transform(request, s)).orElse(request);
  }

  private Object transform(Object request, String stopHeader) {
    Builder builder = request.toBuilder();

    builder
      .getRequestBuilder()
      .setReturnOverrides(
        ReturnOverrides
          .newBuilder()
          .setResponseCode(200)
          .setResponseError("STOP! Message was [" + stopHeader + "]")
          .build()
      );

    return builder.build();
  }

  private Optional<String> getStopHeader(Object request) {
    return Optional
      .ofNullable(request.getRequest().getHeadersOrDefault(STOP_HEADER_NAME, null))
      .filter(s -> !s.isEmpty());
  }
}
