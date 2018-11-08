package proteus.demo.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import reactor.netty.http.server.HttpServer;

@SpringBootApplication
@ComponentScan(value = "proteus.demo")
public class Http {

  public static void main(String... args) {
    SpringApplication.run(Http.class, args);
  }
  
}
