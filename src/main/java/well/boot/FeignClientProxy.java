package well.boot;

import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import well.configuration.FeignHttpsConfig;

@FeignClient(name = "feignClientProxy", configuration = FeignHttpsConfig.class,url = "https://127.0.0.1:443/")
public interface FeignClientProxy {

    @RequestMapping(value = "/ttt", method = RequestMethod.GET)
    @ResponseBody
    String invoke(@RequestParam("json") String json);

    /**
     * 容错处理类，当调用失败时 返回空字符串
     */

}