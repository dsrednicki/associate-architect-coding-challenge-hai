package com.signavio.architect.challenge.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

    private String name;

    private String description;

    private OffsetDateTime finished;

    private OffsetDateTime created;

    @ManyToOne
    private UserEntity reporter;

    @ManyToOne
    private UserEntity assignee;

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

    public UserEntity getReporter() {
        return reporter;
    }

    public void setReporter(final UserEntity reporter) {
        this.reporter = reporter;
    }

    public UserEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(final UserEntity assignee) {
        this.assignee = assignee;
    }
}
