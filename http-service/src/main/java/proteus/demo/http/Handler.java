package proteus.demo.http;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import io.netifi.proteus.spring.core.annotation.Group;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import proteus.demo.color.ColorRequest;
import proteus.demo.color.ColorServiceClient;
import proteus.demo.number.NumberRequest;
import proteus.demo.number.NumberServiceClient;
import proteus.demo.string_service.StringRequest;
import proteus.demo.string_service.StringServiceClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class Handler implements ApplicationRunner {
  private static final Logger logger = LogManager.getLogger(Handler.class);

  @Group("ColorService")
  ColorServiceClient colorService;

  @Group("StringService")
  StringServiceClient stringService;

  @Group("NumberService")
  NumberServiceClient numberService;

  private <T extends com.google.protobuf.GeneratedMessageV3.Builder<?>, V> V convertToProto(
      String string, T builder) {
    try {
      JsonFormat.parser().ignoringUnknownFields().merge(string, builder);
      return (V) builder.build();
    } catch (Exception e) {
      throw Exceptions.bubble(e);
    }
  }

  private String toString(Message message) {
    try {
      return JsonFormat.printer().print(message);
    } catch (Exception e) {
      throw Exceptions.bubble(e);
    }
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    HttpServer.create()
        .port(8080)
        .route(
            routes -> {
              routes
                  .get(
                      "/color",
                      (req, res) -> {
                        Mono<String> color =
                            colorService
                                .getRandomColor(ColorRequest.getDefaultInstance())
                                .map(this::toString);

                        return res.status(HttpResponseStatus.OK).sendString(color);
                      })
                  .post(
                      "/number",
                      (req, res) -> {
                        Flux<String> map =
                            req.receiveContent()
                                .flatMap(
                                    httpContent -> {
                                      NumberRequest request =
                                          convertToProto(
                                              httpContent
                                                  .content()
                                                  .toString(StandardCharsets.UTF_8),
                                              NumberRequest.newBuilder());
                                      return numberService.getRandomNumber(request);
                                    })
                                .map(this::toString);

                        return res.status(HttpResponseStatus.OK).sendString(map);
                      })
                  .post(
                      "/string",
                      (req, res) -> {
                          Flux<String> map =
                              req.receiveContent()
                                  .flatMap(
                                      httpContent -> {
                                          StringRequest request =
                                              convertToProto(
                                                  httpContent
                                                      .content()
                                                      .toString(StandardCharsets.UTF_8),
                                                  StringRequest.newBuilder());
                                          return stringService.getRandomString(request);
                                      })
                                  .map(this::toString);
    
                          return res.status(HttpResponseStatus.OK).sendString(map);
                      });
            })
        .bindUntilJavaShutdown(Duration.ofSeconds(1), disposableServer -> {});
  }
}
