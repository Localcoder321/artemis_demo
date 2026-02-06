package dev.localcoder.artemis_demo.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

@Configuration
public class ArtemisConfig {
    @Bean
    public Queue orderQueue() {
        return new ActiveMQQueue("order.queue");
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager(@Qualifier("jmsConnectionFactory")ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }
}
