package com.measurementpointslocal.api.dto;

public class MeasurementDtoPush {

    private Long criteria;
    private String person;
    private Integer rating;
    private String comment;

    public Long getCriteria() {
        return criteria;
    }

    public void setCriteria(Long criteria) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
