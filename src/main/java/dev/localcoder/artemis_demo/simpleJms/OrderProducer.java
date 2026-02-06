package dev.localcoder.artemis_demo.simpleJms;

import dev.localcoder.artemis_demo.config.MetricsConfig;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final JmsTemplate jmsTemplate;
    private final MetricsConfig.JmsMetrics jmsMetrics;

    public OrderProducer(JmsTemplate jmsTemplate,  MetricsConfig.JmsMetrics jmsMetrics) {
        this.jmsTemplate = jmsTemplate;
        this.jmsMetrics = jmsMetrics;
    }

    public void send(long orderId) {
        System.out.println("PRODUCER CALLED");
        String payload = "{ \"event\": \"ORDER_CREATED\", \"orderId\": " + orderId + " }";

        try {
            jmsTemplate.convertAndSend("order.queue", payload);
            jmsMetrics.incrementMessagesSent();
            System.out.println("Sent: " + payload);
        } catch (Exception e) {
            jmsMetrics.incrementMessagesFailed();
            System.err.println("Failed to send message: " + e.getMessage());
            throw e;
        }
    }
}
