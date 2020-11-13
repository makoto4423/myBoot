package well.configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import well.bean.BootBean;

import java.util.UUID;

@Configuration
public class ScopeConfig {

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public BootBean getBootBean(){
        BootBean bean = new BootBean();
        bean.setS(UUID.randomUUID().toString());
        return bean;
    }

}
