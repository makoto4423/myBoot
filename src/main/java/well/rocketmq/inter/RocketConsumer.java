package well.rocketmq.inter;

import org.apache.rocketmq.client.consumer.listener.MessageListener;

public interface RocketConsumer {
    /**
     * 初始化消费者
     */
    void init();

    void registerMessageListener(MessageListener messageListener);
}
