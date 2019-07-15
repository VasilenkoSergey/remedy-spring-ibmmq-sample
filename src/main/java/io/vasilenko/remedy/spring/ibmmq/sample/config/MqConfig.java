package io.vasilenko.remedy.spring.ibmmq.sample.config;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;

@Configuration
@EnableJms
public class MqConfig {

    @Value("${broker.host}")
    private String brokerHost;

    @Value("${broker.port}")
    private int brokerPort;

    @Value("${broker.manager}")
    private String brokerManager;

    @Value("${broker.channel}")
    private String brokerChannel;

    @Bean
    public MQQueueConnectionFactory connectionFactory() throws JMSException {
        MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();
        connectionFactory.setHostName(brokerHost);
        connectionFactory.setPort(brokerPort);
        connectionFactory.setQueueManager(brokerManager);
        connectionFactory.setChannel(brokerChannel);
        connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(MQQueueConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(MQQueueConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("1-1");
        return factory;
    }
}
