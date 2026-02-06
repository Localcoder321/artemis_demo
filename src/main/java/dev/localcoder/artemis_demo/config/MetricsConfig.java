package dev.localcoder.artemis_demo.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MetricsConfig {
    @Bean
    public JmsMetrics jmsMetrics (MeterRegistry registry) {
        return new JmsMetrics(registry);
    }

    @Component
    public static class JmsMetrics {
        private final Counter messagesSent;
        private final Counter messagesReceived;
        private final Counter messagesProcessed;
        private final Counter messagesFailed;
        private final Timer messageProccessingTime;
        private final MeterRegistry registry;
        public JmsMetrics(MeterRegistry registry) {
            this.registry = registry;

            this.messagesSent = Counter.builder("jms.messages.sent")
                    .description("Total number of JMS messages sent")
                    .tag("queue", "order.queue").register(registry);
            this.messagesReceived = Counter.builder("jms.messages.received")
                    .description("Total number of JMS messages received")
                    .tag("queue", "order.queue").register(registry);
            this.messagesProcessed = Counter.builder("jms.messages.processed")
                    .description("Total number of JMS messages successfully processed")
                    .tag("queue", "order.queue").register(registry);
            this.messagesFailed = Counter.builder("jms.messages.failed")
                    .description("Total number of JMS messages that failed processing")
                    .tag("queue", "order.queue").register(registry);
            this.messageProccessingTime = Timer.builder("jms.messages.processing.time")
                    .description("Time taken to process JMS messages")
                    .tag("queue", "order.queue").register(registry);
        }

        public void incrementMessagesSent() {
            this.messagesSent.increment();
        }

        public void incrementMessagesReceived() {
            this.messagesReceived.increment();
        }

        public void incrementMessagesProcessed() {
            this.messagesProcessed.increment();
        }

        public void incrementMessagesFailed() {
            this.messagesFailed.increment();
        }

        public Timer.Sample startTimer() {
            return Timer.start(registry);
        }

        public void recordProcessingTime(Timer.Sample sample) {
            sample.stop(messageProccessingTime);
        }
    }
}
