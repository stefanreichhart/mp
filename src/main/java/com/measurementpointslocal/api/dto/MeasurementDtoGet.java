package com.measurementpointslocal.api.dto;

import java.time.LocalDateTime;

public class MeasurementDtoGet {

    private Long id;
    private LocalDateTime timestamp;
    private Long criteria;
    private Integer rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCriteria() {
        return criteria;
    }

    public void setCriteria(Long criteria) {
        this.criteria = criteria;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
