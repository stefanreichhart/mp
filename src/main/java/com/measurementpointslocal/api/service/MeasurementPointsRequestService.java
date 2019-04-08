package com.measurementpointslocal.api.service;

import com.measurementpointslocal.api.dto.MeasurementDtoPush;
import com.measurementpointslocal.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.time.LocalDateTime;

@Service
public class MeasurementPointsRequestService {

    public void verifyMeasurement(MeasurementDtoPush dto) {
        if (dto == null) {
            throw new BadRequestException(dto, "undefined");
        }
        if (dto.getRating() == null || dto.getRating() < 0) {
            throw new BadRequestException(dto, "rating must not be null");
        }
        if (dto.getCriteria() == null) {
            throw new BadRequestException(dto, "criteria must not be null");
        }
        if (StringUtils.isBlank(dto.getPerson())) {
            throw new BadRequestException(dto, "person must not be null or empty");
        }
    }

    public void verifyMeasurement(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new BadRequestException("invalid date range");
        }
        if (from != null && LocalDateTime.now().isBefore(from)) {
            throw new BadRequestException("invalid date range");
        }
    }

    public void verifyCriteria(String name) {
        if (StringUtils.isBlank(name)) {
            throw new BadRequestException("name must not be empty");
        }
    }

}
