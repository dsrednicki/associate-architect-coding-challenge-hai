package com.signavio.architect.challenge.repository;

import jakarta.persistence.Entity;

import java.time.OffsetDateTime;

@Entity
public class TaskEntity extends BaseEntity {

    private String name;

    private String description;

    private OffsetDateTime finished;

    private OffsetDateTime created;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getFinished() {
        return finished;
    }

    public void setFinished(OffsetDateTime finished) {
        this.finished = finished;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }
}
