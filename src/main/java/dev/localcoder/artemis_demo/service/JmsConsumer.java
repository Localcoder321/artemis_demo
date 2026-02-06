package dev.localcoder.artemis_demo.service;

import dev.localcoder.artemis_demo.config.MetricsConfig;
import dev.localcoder.artemis_demo.domain.InboxMessage;
import dev.localcoder.artemis_demo.repository.InboxRepository;
import io.micrometer.core.instrument.Timer;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.transaction.Transactional;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsConsumer {
    private final InboxRepository inboxRepository;
    private final MetricsConfig.JmsMetrics jmsMetrics;

    public JmsConsumer(InboxRepository inboxRepository, MetricsConfig.JmsMetrics jmsMetrics) {
        this.inboxRepository = inboxRepository;
        this.jmsMetrics = jmsMetrics;
    }

    @JmsListener(destination = "order.queue")
    @Transactional
    public void receiveMessage(Message message) throws Exception {
        Timer.Sample sample = jmsMetrics.startTimer();
        jmsMetrics.incrementMessagesReceived();


        try {
            String messageId = message.getJMSMessageID();
            String payload = ((TextMessage) message).getText();

            if(inboxRepository.existsByMessage(messageId)) {
                return;
            }

            inboxRepository.save(new InboxMessage(messageId));
            System.out.println("Processed message: " + payload);

            jmsMetrics.incrementMessagesProcessed();
            jmsMetrics.recordProcessingTime(sample);
        } catch (Exception e) {
            jmsMetrics.incrementMessagesFailed();
            System.err.println("Failed to send message: " + e.getMessage());
            throw e;
        }
    }
}
