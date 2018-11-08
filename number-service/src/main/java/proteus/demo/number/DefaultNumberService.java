package proteus.demo.number;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class DefaultNumberService implements NumberService {
  private static final Logger logger = LoggerFactory.getLogger(DefaultNumberService.class);

  @Override
  public Mono<NumberResponse> getRandomNumber(NumberRequest message, ByteBuf metadata) {
    return Mono.fromSupplier(
        () -> {
          int i = ThreadLocalRandom.current().nextInt(message.getMin(), message.getMax());
          logger.info("generated ranomd number {}", i);
          return NumberResponse.newBuilder().setResult(i).build();
        });
  }
}
