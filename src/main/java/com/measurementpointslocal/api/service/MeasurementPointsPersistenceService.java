package com.measurementpointslocal.api.service;

import com.measurementpointslocal.api.dto.CriteriaDto;
import com.measurementpointslocal.api.dto.MeasurementDtoGet;
import com.measurementpointslocal.api.dto.MeasurementDtoPush;
import com.measurementpointslocal.exception.BadRequestException;
import com.measurementpointslocal.persistence.model.Criteria;
import com.measurementpointslocal.persistence.model.Measurement;
import com.measurementpointslocal.persistence.model.Person;
import com.measurementpointslocal.persistence.repository.CriteriaRepository;
import com.measurementpointslocal.persistence.repository.MeasurementRepository;
import com.measurementpointslocal.persistence.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeasurementPointsPersistenceService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private PersonRepository personRepository;

    public Measurement saveMeasurement(MeasurementDtoPush dto) {
        Measurement measurement = new Measurement();
        measurement.setPerson(mapPerson(dto));
        measurement.setCriteria(mapCriteria(dto));
        measurement.setRating(dto.getRating());
        measurement.setComment(dto.getComment());
        return measurementRepository.save(measurement);
    }

    private Criteria mapCriteria(MeasurementDtoPush dto) {
        Optional<Criteria> existingCriteria = criteriaRepository.findById(dto.getCriteria());
        if (existingCriteria.isPresent()) {
            return existingCriteria.get();
        } else {
            throw new BadRequestException(dto, "criteria does not exist");
        }
    }

    private Person mapPerson(MeasurementDtoPush dto) {
        Optional<Person> existingPerson = personRepository.findByName(dto.getPerson());
        if (existingPerson.isPresent()) {
            return existingPerson.get();
        } else {
            Person newPerson = new Person();
            newPerson.setName(dto.getPerson());
            return personRepository.save(newPerson);
        }
    }

    public List<MeasurementDtoGet> getMeasurementList(LocalDateTime from, LocalDateTime to) {
        List<Measurement> measurements;
        if (from != null && to != null) {
            measurements = measurementRepository.findFromTo(from, to);
        } else if (from != null) {
            measurements = measurementRepository.findFrom(from);
        } else if (to != null) {
            measurements = measurementRepository.findTo(to);
        } else {
            measurements = measurementRepository.findAll();
        }
        return measurements.stream()
                .map(each -> mapMeasurement(each))
                .collect(Collectors.toList());
    }

    private MeasurementDtoGet mapMeasurement(Measurement measurement) {
        MeasurementDtoGet dto = new MeasurementDtoGet();
        dto.setId(measurement.getId());
        dto.setTimestamp(measurement.getCreated());
        dto.setCriteria(measurement.getCriteria().getId());
        dto.setRating(measurement.getRating());
        return dto;
    }

    public List<CriteriaDto> getCriteriaList() {
        return criteriaRepository.findAll().stream()
                .map(each -> mapCriteria(each))
                .collect(Collectors.toList());
    }

    private CriteriaDto mapCriteria(Criteria criteria) {
        CriteriaDto dto = new CriteriaDto();
        dto.setId(criteria.getId());
        dto.setName(criteria.getName());
        return dto;
    }

    public Criteria saveCriteria(String name) {
        Optional<Criteria> existingCriteria = criteriaRepository.findByName(name);
        if (existingCriteria.isPresent()) {
            return existingCriteria.get();
        } else {
            Criteria newCriteria = new Criteria();
            newCriteria.setName(name);
            return criteriaRepository.save(newCriteria);
        }
    }
}
