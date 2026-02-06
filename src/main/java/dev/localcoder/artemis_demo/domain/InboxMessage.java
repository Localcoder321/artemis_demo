package dev.localcoder.artemis_demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inbox")
public class InboxMessage {
    @Id @GeneratedValue @Getter @Setter
    private Long id;
    @Column(unique = true)
    @Getter @Setter
    private String message;

    public InboxMessage() {}
    public InboxMessage(String message) {
        this.message = message;
    }
}
