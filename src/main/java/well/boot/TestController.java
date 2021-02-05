package well.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import well.annotation.MyAnnotation;
import well.bean.BootBean;
import well.rocketmq.DefaultConsumerMQ;
import well.util.SpringContextUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private FeignClientProxy proxy;
//    @Resource
//    private DefaultMQProducer defaultMQProducer;
    @Value("${value:123}")
    private String value;
    @Resource
    private TomcatServletWebServerFactory factory;
    @Resource
    private BootBean bean;
    // spring的注入不是单例的吗?但是HttpServletRequest每次请求都会注入当前请求的request
    // 但是这个类在内存的位置从未发生变化，理解为request这个东西只是个代理，
    // 每次去获取到实际request时，都会通过代理获取到真正的request
    @Resource
    private HttpServletRequest request;

    private static final Logger logger  = LoggerFactory.getLogger(TestController.class);

    @GetMapping("")
    public void ttt(HttpServletResponse request) throws IOException, KeeperException, InterruptedException, ClassNotFoundException {
        proxy.invoke("ttttt");
        int i = 0;
        // JSONObject jsonObject = JSON.parseObject("{\n\t\"data\":{\n\t\t\"NODE\":{\n\t\t\t\"clusterName\":\"test\",\n\t\t\t\"ip\":\"10.8.4.188\",\n\t\t\t\"myid\":2,\n\t\t\t\"port\":9103\n\t\t},\n\t\t\"CONFIGCENTER\":{\n\t\t\t\"configType\":\"zk\",\n\t\t\t\"gatherTime\":\"2020-05-28 10:12:16\",\n\t\t\t\"pathRoot\":\"/ats-config-center-web/test\",\n\t\t\t\"serversList\":[\n\t\t\t\t\"10.8.4.167:5566\"\n\t\t\t]\n\t\t}\n\t},\n\t\"msgCode\":\"0\"\n}");
        logger.info("bye bye world");
//        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 60 * 60 * 1000, watchedEvent -> {
//            Watcher.Event.KeeperState state = watchedEvent.getState();
//        });
//        byte[] bytes = zk.getData("/agree/AFA4J/config/tratest/M0001/M0001",false,null);
//        ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
//        ObjectInputStream ois = new ObjectInputStream(bis);
//        Object obj = ois.readObject();
//        ois.close();
//        bis.close();
    }

    @GetMapping("/aaa")
    public void ddd(){
        this.proxy.invoke("kkk");
    }

    @GetMapping("/test")
    public void test(){
        Map<String,Object> map = SpringContextUtil.getSpringContext().getBeansWithAnnotation(MyAnnotation.class);
    }

    @GetMapping("/vvv")
    public void test1(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        Message m = new Message("TopicTest", "tags1", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        // 发送消息到一个Broker
        SendResult sendResult = defaultMQProducer.send(m);
    }

    @GetMapping("/sync")
    public void sync() throws InterruptedException {
        synchronized (bean){
            bean.setS("456");
            Thread.sleep(10000000);
            bean.setS("789");
        }
    }

    @GetMapping("/sync1")
    public void sync1(){
        logger.info(bean.getS());
    }
}
