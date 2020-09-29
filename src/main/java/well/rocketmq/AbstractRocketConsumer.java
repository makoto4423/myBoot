package well.rocketmq;

import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import well.rocketmq.inter.RocketConsumer;

public abstract class AbstractRocketConsumer implements RocketConsumer {

    protected String topics;
    protected String tags;
    protected MessageListener messageListener;
    protected String consumerTitle;
    protected MQPushConsumer mqPushConsumer;

    public void necessary(String topics, String tags, String consumerTitle) {
        this.topics = topics;
        this.tags = tags;
        this.consumerTitle = consumerTitle;
    }

    public abstract void init();

    @Override
    public void registerMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

}
