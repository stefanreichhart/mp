package com.measurementpointslocal.api.controller;

import com.measurementpointslocal.api.dto.CriteriaDto;
import com.measurementpointslocal.api.dto.MeasurementDtoGet;
import com.measurementpointslocal.api.dto.MeasurementDtoPush;
import com.measurementpointslocal.api.service.MeasurementPointsPersistenceService;
import com.measurementpointslocal.api.service.MeasurementPointsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mp")
public class MeasurementPointsController {

    @Autowired
    private MeasurementPointsRequestService verification;

    @Autowired
    private MeasurementPointsPersistenceService persistence;

    @GetMapping("/criteria")
    public ResponseEntity<List<CriteriaDto>> getCriteriaList() {
        return new ResponseEntity(persistence.getCriteriaList(), HttpStatus.OK);
    }

    // TODO: return criteria
    @PutMapping("/criteria")
    public ResponseEntity<CriteriaDto> saveCriteria(@RequestBody String name) {
        verification.verifyCriteria(name);
        persistence.saveCriteria(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/measurement")
    public ResponseEntity<List<MeasurementDtoGet>> getMeasurementList(
            @RequestParam(value = "from", required = false) LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to) {
        verification.verifyMeasurement(from, to);
        return new ResponseEntity(persistence.getMeasurementList(from, to), HttpStatus.OK);
    }

    // TODO return measurement
    @PostMapping("/measurement")
    public ResponseEntity<MeasurementDtoPush> saveMeasurement(@RequestBody MeasurementDtoPush dto) {
        verification.verifyMeasurement(dto);
        persistence.saveMeasurement(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
