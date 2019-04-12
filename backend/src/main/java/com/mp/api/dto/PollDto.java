package com.mp.api.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PollDto {

    private UUID id;

    private String title;

    private String description;

    private List<CriteriaDto> criterias;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CriteriaDto> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<CriteriaDto> criterias) {
        this.criterias = criterias;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

}
