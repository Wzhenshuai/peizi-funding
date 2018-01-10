
package com.icaopan.customer.service.impl;

import com.icaopan.sys.dao.DictMapper;
import com.icaopan.sys.model.Dict;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rabbitConfiguration")
public class RabbitConfiguration {
    @Autowired
    private DictMapper dictMapper;

    private static String CLIENT_SYNC_RCP_Q = "client.sync.queue";
    private static RabbitTemplate rabbitTemplate = null;
    private static RabbitAdmin rabbitAdmin = null;
    private static CachingConnectionFactory connectionFactory = null;

    /**
     *
     */

    public RabbitAdmin getRabbitAdmin() {
        connectionFactory = newConnectionFactory();
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        //rabbitTemplate.setMessageConverter(new ElfMessageConverter());
        rabbitTemplate.setRoutingKey(CLIENT_SYNC_RCP_Q);
        rabbitTemplate.setReplyTimeout(-1);
        rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;

    }

    private CachingConnectionFactory newConnectionFactory() {

        Dict dictDemo=new Dict();
        dictDemo.setType("rabbitMQ");
        List<Dict> dictList = dictMapper.findList(dictDemo);

        ConnectionFactory factory = new ConnectionFactory();
        for (Dict dict : dictList) {
            if (StringUtils.equals(dict.getLabel(), "username")) {
                factory.setUsername(dict.getValue());
            } else if (StringUtils.equals(dict.getLabel(), "password")) {
                factory.setPassword(dict.getValue());
            } else if (StringUtils.equals(dict.getLabel(), "host")) {
                factory.setHost(dict.getValue());
            }
        }

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory.getHost());
        connectionFactory.setRequestedHeartBeat(120);
        connectionFactory.setUsername(factory.getUsername());
        connectionFactory.setPassword(factory.getPassword());

        return connectionFactory;
    }

}
