package dev.localcoder.artemis_demo.controller;

import dev.localcoder.artemis_demo.simpleJms.OrderProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/{id}")
    public void send(@PathVariable long id) {
        System.out.println("HTTP RECEIVED: " + id);
        orderProducer.send(id);
    }
}
