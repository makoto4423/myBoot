package well.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import well.annotation.MyAnnotation;
import well.bean.BootBean;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@RestController
public class AbcController {

    private static Logger logger  = LoggerFactory.getLogger(AbcController.class);

    @PostMapping
    public void abc(String json){
        logger.info(json);
    }

    @GetMapping("/invoke")
    public void invoke(HttpServletRequest request) throws Exception, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<AbcController> clazz  = (Class<AbcController>) Class.forName("well.boot.AbcController");
        Constructor<AbcController> constructor = clazz.getConstructor();
        AbcController obj = constructor.newInstance();
        obj.kkk(request);
    }

    @GetMapping("/kill")
    @MyAnnotation(name = "dd")
    public void kkk(HttpServletRequest request){

    }

    @GetMapping("well")
    public void wellGet(BootBean bean){

    }

    @PostMapping("/zx")
    public void kkkk(@RequestBody String json){

    }

    @PostMapping("well")
    public void wellPost(@RequestBody BootBean bean){

    }

    @PostMapping("/well/test")
    public void test(@RequestBody Map<String, Object> map){

    }
}
