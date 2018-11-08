package proteus.demo.char_service;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class DefaultCharService implements CharService {
  private static final Logger logger = LoggerFactory.getLogger(DefaultCharService.class);

  private static final char[] CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

  @Override
  public Flux<CharResponse> streamCharacters(CharRequest message, ByteBuf metadata) {
    int size = message.getSize();

    logger.info("sending {} characters", size);

    return Flux.generate(
        sink -> {
          int i = ThreadLocalRandom.current().nextInt(0, CHARACTERS.length);
          char c = CHARACTERS[i];
          logger.info("sending character {}", c);
          sink.next(CharResponse.newBuilder().setMessage(String.valueOf(c)).build());
        });
  }
}
