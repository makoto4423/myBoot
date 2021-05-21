package well.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Configuration
public class BinderConfig {

//    @InitBinder("*")
//    public void initBinder(WebDataBinder binder){
//        binder.setDisallowedFields("password");
//    }
}
