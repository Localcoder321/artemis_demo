package dev.localcoder.artemis_demo.repository;

import dev.localcoder.artemis_demo.domain.InboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxRepository extends JpaRepository<InboxMessage, Long> {
    boolean existsByMessage(String message);
}
