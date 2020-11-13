package well.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SleepService {

    public String doSleep() throws InterruptedException {
        log.info("go to bed");
        Thread.sleep(1000);
        log.info("wake up");
        return "wake up";
    }

}
