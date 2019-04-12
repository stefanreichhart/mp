package com.mp.api.mapper;

import com.mp.api.dto.PollDto;
import com.mp.persistence.model.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollMapper {

    @Autowired
    private CriteriaMapper criteriaMapper;

    public PollDto mapPoll(Optional<Poll> optionalPoll) {
        if (optionalPoll.isPresent()) {
            return mapPoll(optionalPoll.get());
        }
        return null;
    }

    public List<PollDto> mapPolls(List<Poll> polls) {
        return polls == null ? new ArrayList<>() : polls.stream()
                .filter(Objects::nonNull)
                .map(this::mapPoll)
                .collect(Collectors.toList());
    }

    public PollDto mapPoll(Poll poll) {
        PollDto dto = new PollDto();
        dto.setId(poll.getId());
        dto.setTitle(poll.getTitle());
        dto.setDescription(poll.getDescription());
        dto.setValidFrom(poll.getValidFrom());
        dto.setValidTo(poll.getValidTo());
        dto.setCriterias(criteriaMapper.mapCriterias(poll.getCriterias()));
        return dto;
    }


}
