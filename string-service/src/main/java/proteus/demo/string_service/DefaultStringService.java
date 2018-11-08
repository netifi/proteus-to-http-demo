package proteus.demo.string_service;

import io.netifi.proteus.spring.core.annotation.Group;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import proteus.demo.char_service.CharRequest;
import proteus.demo.char_service.CharServiceClient;
import reactor.core.publisher.Mono;

@Component
public class DefaultStringService implements StringService {
  private static final Logger logger = LoggerFactory.getLogger(DefaultStringService.class);

  @Group("CharacterService")
  CharServiceClient client;

  @Override
  public Mono<StringResponse> getRandomString(StringRequest message, ByteBuf metadata) {
    int size = message.getSize();
    logger.info("generate random string {} characters long", size);

    return client
        .streamCharacters(CharRequest.newBuilder().setSize(size).build())
        .limitRequest(size)
        .reduce(
            new StringBuilder(),
            (stringBuilder, charResponse) -> stringBuilder.append(charResponse.getMessage()))
        .map(
            stringBuilder -> {
              String s = stringBuilder.toString();
              logger.info("generated string {}");
              return StringResponse.newBuilder().setMessage(s).build();
            });
  }
}
