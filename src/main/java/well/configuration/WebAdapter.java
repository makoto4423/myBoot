package well.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import well.filter.MyInterceptor;

import javax.annotation.Resource;

// @Component
public class WebAdapter implements WebMvcConfigurer {
//    @Resource
//    private MyInterceptor myInterceptor;
//
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(myInterceptor);
//    }

}
