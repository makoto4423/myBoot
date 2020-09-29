package well.boot;

import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.Request;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        okhttp3.OkHttpClient client =  new okhttp3.OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .build();
//        Request request = new Request.Builder()
//                .url("http://10.8.4.167:8763/eureka/apps")
//                .method("GET", null)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .build();
//        try {
//            client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return client;
    }
}
