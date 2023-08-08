package com.usersapp.base;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Base Entity abstract class containing UUID field
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", updatable = false, nullable = false)
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
