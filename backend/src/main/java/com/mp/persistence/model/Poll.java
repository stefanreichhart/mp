package com.mp.persistence.model;

import com.mp.persistence.utils.ModelValidator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "poll")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Criteria> criterias = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Measurement> measurements = new ArrayList<>();

    @Column(name = "validTo")
    private LocalDateTime validTo;

    @Column(name = "validFrom")
    private LocalDateTime validFrom;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updated;

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        ModelValidator.requireNotNullNotEmpty(title, "poll.title");
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Criteria> getCriterias() {
        return criterias != null ? new ArrayList<>(criterias) : new ArrayList<>();
    }

    public List<Measurement> getMeasurements() {
        return measurements != null ? new ArrayList<>(measurements) : new ArrayList<>();
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        ModelValidator.requireDateRange(validFrom, validTo);
        this.validTo = validTo;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        ModelValidator.requireDateRange(validFrom, validTo);
        this.validFrom = validFrom;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void addCriteria(Criteria criteria) {
        ModelValidator.requireNotNull(criteria, "criteria");
        if (!criterias.contains(criteria)) {
            if (criteria.getPoll() != null && !this.equals(criteria.getPoll())) {
                throw new IllegalArgumentException("criteria is already child of another poll");
            }
            ModelValidator.requireUnique(criterias, criteria, (Criteria c) -> c.getName());
            if (criterias.add(criteria)) {
                criteria.setPoll(this);
            } else {
                throw new IllegalStateException("criteria could not be added to this poll");
            }
        }
    }

    public void removeCriteria(Criteria criteria) {
        ModelValidator.requireNotNull(criteria, "criteria");
        if (this.criterias.remove(criteria)) {
            criteria.setPoll(null);
        }
    }

}
