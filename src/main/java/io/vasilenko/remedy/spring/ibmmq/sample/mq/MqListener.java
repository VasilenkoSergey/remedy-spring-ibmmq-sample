package io.vasilenko.remedy.spring.ibmmq.sample.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class MqListener {

    private final Logger log = LoggerFactory.getLogger(MqListener.class);

    @JmsListener(destination = "${listener.destination}")
    public void receiveMessage(TextMessage message) throws JMSException {
        log.info("message: {}", message.getText());
    }
}
