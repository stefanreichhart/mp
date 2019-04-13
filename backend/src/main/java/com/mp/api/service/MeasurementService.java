package com.mp.api.service;

import com.mp.api.dto.MeasurementDto;
import com.mp.persistence.model.Measurement;
import com.mp.persistence.repository.CriteriaRepository;
import com.mp.persistence.repository.MeasurementRepository;
import com.mp.persistence.repository.PersonRepository;
import com.mp.persistence.repository.PollRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeasurementService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public List<Measurement> addAll(List<MeasurementDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        return measurementRepository.saveAll(newMeasurements(dtos));
    }

    private List<Measurement> newMeasurements(List<MeasurementDto> dtos) {
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(each -> newMeasurement(each))
                .collect(Collectors.toList());
    }

    private Measurement newMeasurement(MeasurementDto dto) {
        Measurement measurement = new Measurement();
        measurement.setCriteria(criteriaRepository.findById(dto.getCriteria()).get());
        if (!StringUtils.isBlank(dto.getPerson())) {
            measurement.setPerson(personRepository.findByName(dto.getPerson()).orElse(null));
        }
        measurement.setRating(dto.getRating());
        return measurement;
    }

    @Transactional
    public Measurement modify(UUID id, Integer rating) {
        Measurement measurement = measurementRepository.findById(id).get();
        measurement.setRating(rating);
        return measurementRepository.save(measurement);
    }

    @Transactional
    public void removeAll(List<UUID> ids) {
        measurementRepository.deleteByIds(ids);
    }

}
