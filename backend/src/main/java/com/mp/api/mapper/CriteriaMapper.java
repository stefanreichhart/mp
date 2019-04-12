package com.mp.api.mapper;

import com.mp.api.dto.CriteriaDto;
import com.mp.persistence.model.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CriteriaMapper {

    public CriteriaDto mapCriteria(Optional<Criteria> optionalCriteria) {
        if (optionalCriteria.isPresent()) {
            return mapCriteria(optionalCriteria.get());
        }
        return null;
    }

    public List<CriteriaDto> mapCriterias(List<Criteria> criterias) {
        return criterias == null ? new ArrayList<>() : criterias.stream()
                .filter(Objects::nonNull)
                .map(this::mapCriteria)
                .collect(Collectors.toList());
    }

    public CriteriaDto mapCriteria(Criteria criteria) {
        CriteriaDto dto = new CriteriaDto();
        dto.setId(criteria.getId());
        dto.setName(criteria.getName());
        return dto;
    }

}
