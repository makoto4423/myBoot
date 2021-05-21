package well.boot;

import lombok.Data;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.function.Supplier;

@RestController
@RequestMapping("/defined")
public class DefinedController {

    @GetMapping("/here")
    public RestResultMessage<?> eurekaApp(){
        RestTemplate template = new RestTemplate();
        String s = template.getForObject("http://127.0.0.1:444/authorities/query?type=123",String.class);
        return new RestResultMessage<>(s);
    }

    @lombok.Data
    static class RestResultMessage<T> {

        private T content;
        private boolean success;
        private String errorCode;
        private String errorMessage;
        private Collection<String> errorTips;

        public RestResultMessage(Supplier<? extends Throwable> supplier) {
            this.setContent(null);
            this.setSuccess(false);
            this.setErrorMessage(supplier.get().getLocalizedMessage());
        }

        public RestResultMessage(T t) {
            this.setContent(t);
            this.setSuccess(true);
        }

        public RestResultMessage(T t,int i) {
            this.setContent(t);
            this.setSuccess(false);
        }
    }

    @Data
    static class Fe{
        String id;
        String name;
    }
}
