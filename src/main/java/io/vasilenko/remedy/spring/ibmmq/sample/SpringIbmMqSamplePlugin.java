package io.vasilenko.remedy.spring.ibmmq.sample;

import com.bmc.arsys.api.Value;
import com.bmc.arsys.pluginsvr.plugins.ARFilterAPIPlugin;
import com.bmc.arsys.pluginsvr.plugins.ARPluginContext;
import io.vasilenko.remedy.spring.ibmmq.sample.mq.MqSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
public class SpringIbmMqSamplePlugin extends ARFilterAPIPlugin {

    private static final int INPUT_MESSAGE_VALUE_INDEX = 0;

    private final Logger log = LoggerFactory.getLogger(SpringIbmMqSamplePlugin.class);

    private AnnotationConfigApplicationContext applicationContext;
    private MqSender sender;

    @Autowired
    public void setSender(MqSender sender) {
        this.sender = sender;
    }

    @Override
    public void initialize(ARPluginContext context) {
        applicationContext = new AnnotationConfigApplicationContext(SpringIbmMqSamplePlugin.class);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
        log.info("initialized");
    }

    @Override
    public List<Value> filterAPICall(ARPluginContext arPluginContext, List<Value> list) {
        String message = String.valueOf(list.get(INPUT_MESSAGE_VALUE_INDEX));
        sender.sendMessage(message);
        List<Value> output = new ArrayList<>();
        output.add(new Value(message + " sent"));
        return output;
    }

    @Override
    public void terminate(ARPluginContext context) {
        applicationContext.close();
    }
}
