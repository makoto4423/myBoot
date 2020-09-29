package well.configuration;

import feign.Client;
import feign.Feign;
import feign.Retryer;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class FeignHttpsConfig {

    @Bean
    public Feign.Builder builder() throws NoSuchAlgorithmException, KeyManagementException {
        X509TrustManager trustManager = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] xcs, String str) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String str) {
            }
        };
        SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
        ctx.init(null, new TrustManager[]{trustManager}, null);
        // SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
        return Feign.builder()
                .client(new Client.Default(ctx.getSocketFactory(), NoopHostnameVerifier.INSTANCE))
                .retryer(new Retryer.Default(5000, 5000, 3));
    }


}
