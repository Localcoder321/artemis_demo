package dev.localcoder.artemis_demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "outbox")
public class OutboxMessage {
    @Id @GeneratedValue @Getter
    private Long id;
    @Setter
    private Long aggregateId;
    @Getter @Setter
    private boolean processed;
    @Getter @Setter
    private String message;
}
