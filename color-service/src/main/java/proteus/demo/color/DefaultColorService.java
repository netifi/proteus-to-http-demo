package proteus.demo.color;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class DefaultColorService implements ColorService {
  private static final Logger logger = LogManager.getLogger(DefaultColorService.class);

  private static final String[] COLOR = new String[] {"RED", "WHITE", "BLUE", "GREEN"};

  @Override
  public Mono<ColorResponse> getRandomColor(ColorRequest message, ByteBuf metadata) {
    return Mono.fromSupplier(
        () -> {
          int i = ThreadLocalRandom.current().nextInt(COLOR.length);
          String color = COLOR[i];
          logger.info("returning color {}", color);
          return ColorResponse.newBuilder().setMessage(color).build();
        });
  }
}
