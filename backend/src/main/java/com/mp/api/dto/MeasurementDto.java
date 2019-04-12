package com.mp.api.dto;

import java.util.UUID;

public class MeasurementDto {

    private UUID poll;

    private UUID criteria;

    private String person;

    private Integer rating;

    public UUID getPoll() {
        return poll;
    }

    public void setPoll(UUID poll) {
        this.poll = poll;
    }

    public UUID getCriteria() {
        return criteria;
    }

    public void setCriteria(UUID criteria) {
        this.criteria = criteria;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
