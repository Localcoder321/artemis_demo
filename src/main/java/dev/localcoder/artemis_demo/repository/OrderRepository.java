package dev.localcoder.artemis_demo.repository;

import dev.localcoder.artemis_demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
