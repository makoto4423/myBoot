package well.makoto;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.TomcatServletWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.SpringServletContainerInitializer;
import well.bean.BootBean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class HelloHttpsConfig {

//    @Value("${server.http.port}")
//    private int HTTP_PORT;
//    @Value("${server.port}")
//    private int HTTPS_PORT;
//
//
////    @PostConstruct
////    public void init(){
////        factory.addAdditionalTomcatConnectors(connector());
////    }
//
//
//    public Connector connector(){
//        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8888);
//        connector.setSecure(false);
//        connector.setRedirectPort(444);
//        return connector;
//    }
//
//    @Bean
//    @ConditionalOnProperty("server.http.port")
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
//        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint=new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection=new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(connector());
//        return tomcat;
//    }
}
