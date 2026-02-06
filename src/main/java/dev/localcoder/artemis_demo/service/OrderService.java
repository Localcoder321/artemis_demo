package dev.localcoder.artemis_demo.service;

import dev.localcoder.artemis_demo.domain.Order;
import dev.localcoder.artemis_demo.domain.OutboxMessage;
import dev.localcoder.artemis_demo.repository.OrderRepository;
import dev.localcoder.artemis_demo.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public void createOrder(String name) {
        Order order = orderRepository.save(new Order(name));

        OutboxMessage message = new OutboxMessage();
        message.setAggregateId(order.getId());
        message.setMessage("Order created: " + order.getId());

        outboxRepository.save(message);
    }
}
