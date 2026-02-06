package dev.localcoder.artemis_demo.repository;

import dev.localcoder.artemis_demo.domain.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxMessage, Long> {
    List<OutboxMessage> findByProcessedFalse();
    Long countByProcessedFalse();
}
