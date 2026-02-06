package dev.localcoder.artemis_demo.scheduler;

import dev.localcoder.artemis_demo.config.MetricsConfig;
import dev.localcoder.artemis_demo.domain.OutboxMessage;
import dev.localcoder.artemis_demo.repository.OutboxRepository;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxSchedular {
    private final OutboxRepository outboxRepository;
    private final JmsTemplate jmsTemplate;
    private final MetricsConfig.JmsMetrics jmsMetrics;
    private final MeterRegistry meterRegistry;

    public OutboxSchedular(JmsTemplate jmsTemplate,
                           OutboxRepository outboxRepository,
                           MetricsConfig.JmsMetrics jmsMetrics,
                           MeterRegistry meterRegistry) {
        this.jmsTemplate = jmsTemplate;
        this.outboxRepository = outboxRepository;
        this.jmsMetrics = jmsMetrics;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void publish() {
        List<OutboxMessage> messages = outboxRepository.findByProcessedFalse();

        for(OutboxMessage message : messages) {
            try {
                jmsTemplate.convertAndSend("order.queue", message.getMessage());
                message.setProcessed(true);
                outboxRepository.save(message);
                jmsMetrics.incrementMessagesSent();
            } catch (Exception ex) {
                jmsMetrics.incrementMessagesFailed();
                System.err.println("Failed to send message: " + ex.getMessage());
            }
        }

        if(!messages.isEmpty()) {
            System.out.println("Published " + messages.size() + " outbox messages");
        }

        long pendingMessages = outboxRepository.countByProcessedFalse();
        meterRegistry.gauge("outbox.pending.messages", pendingMessages);
    }
}
