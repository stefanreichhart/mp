package com.mp.api.controller;

import com.mp.api.dto.MeasurementDto;
import com.mp.api.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @PostMapping("/add")
    public ResponseEntity addMeasurements(@RequestBody List<MeasurementDto> dtos) {
        measurementService.addAll(dtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/modify/{rating}")
    public ResponseEntity modifyMeasurement(@PathVariable("id") UUID id, @PathVariable("rating") Integer rating) {
        measurementService.modify(id, rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity removeMeasurements(@RequestBody List<UUID> ids) {
        measurementService.removeAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
