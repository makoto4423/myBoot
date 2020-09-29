package well.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext springContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.springContext = applicationContext;
    }

    public static ApplicationContext getSpringContext(){
        return springContext;
    }
}
