package dev.localcoder.artemis_demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue @Getter
    private Long id;
    private String name;

    public Order() {}
    public Order(String name) {
        this.name = name;
    }
}
