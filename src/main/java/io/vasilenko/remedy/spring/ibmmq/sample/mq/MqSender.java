package io.vasilenko.remedy.spring.ibmmq.sample.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqSender {

    private final Logger log = LoggerFactory.getLogger(MqSender.class);

    @Value("${sender.destination}")
    private String destination;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public MqSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        log.info("message: {}", message);
        jmsTemplate.convertAndSend(destination, message);
    }
}
