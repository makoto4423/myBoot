package well.boot;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import well.annotation.MyAnnotation;
import well.bean.BootBean;
import well.service.SleepService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@RestController
@RequestMapping("/abc")
public class AbcController {

    private BootBean bean;

    @Resource
    private SleepService sleepService;

    private ExecutorService pool = Executors.newFixedThreadPool(10);

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
    @MyAnnotation("eeee")
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

    @GetMapping("/makoto")
    public void makoto(){
        logger.info(bean.getS());
    }

    @Autowired
    public void setBean(BootBean bean){
        this.bean = bean;
    }

    @GetMapping("/sleep")
    public DeferredResult<String> doSleep(){
        DeferredResult<String> result = new DeferredResult<>(10000L);
        result.onCompletion(()-> log.info("complete"));
        result.onError((t)->{
            log.info("error");
        });
        result.onTimeout(()->{
            log.info("timeout");
        });
        pool.submit(()->{
            try {
                result.setResult(sleepService.doSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    // :[\d]+ 正则表达式，表明namespace必须是数字
    @GetMapping("/namespace/{namespace:[\\d]*p}")
    public void namespace(@PathVariable String namespace){

    }
}
