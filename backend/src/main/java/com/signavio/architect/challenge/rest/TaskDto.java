package com.signavio.architect.challenge.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class TaskDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private ZonedDateTime finished;

    @JsonProperty
    private ZonedDateTime created;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreated(ZonedDateTime time) {
        this.created = time;
    }

    public void setFinished(ZonedDateTime finished) {
        this.finished = finished;
    }

    public Object getFinished() {
        return finished;
    }
}
