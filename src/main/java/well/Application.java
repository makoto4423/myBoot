package well;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.vm.VM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @ComponentScan("makoto")
@Slf4j
@SpringBootApplication
@EnableFeignClients
//
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        String s = VM.current().details();
        log.info("----------start----------");
    }
}