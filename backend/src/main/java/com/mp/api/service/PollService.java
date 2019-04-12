package com.mp.api.service;

import com.mp.api.dto.CriteriaDto;
import com.mp.api.dto.PollDto;
import com.mp.persistence.model.Criteria;
import com.mp.persistence.model.Poll;
import com.mp.persistence.repository.CriteriaRepository;
import com.mp.persistence.repository.MeasurementRepository;
import com.mp.persistence.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Transactional
    public Poll addPoll(PollDto dto) {
        Poll newPoll = new Poll();
        newPoll.setTitle(dto.getTitle());
        newPoll.setDescription(dto.getDescription());
        newPoll.setValidFrom(dto.getValidFrom());
        newPoll.setValidTo(dto.getValidTo());
        newPoll.setCriterias(newCriterias(dto.getCriterias()));
        return pollRepository.save(newPoll);
    }

    private List<Criteria> newCriterias(List<CriteriaDto> dtos) {
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(dto -> {
                    Criteria newCriteria = new Criteria();
                    newCriteria.setName(dto.getName());
                    return newCriteria;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Poll modifyPoll(PollDto dto) {
        Optional<Poll> poll = pollRepository.findByUuid(dto.getId());
        if (poll.isPresent()) {
            return modifyPoll(poll.get(), dto);
        }
        return null;
    }

    private Poll modifyPoll(Poll poll, PollDto dto) {
        poll.setTitle(dto.getTitle());
        poll.setDescription(dto.getDescription());
        poll.setValidFrom(dto.getValidFrom());
        poll.setValidTo(dto.getValidTo());
        // TODO criterias
        return pollRepository.save(poll);
    }

    @Transactional
    public void removePoll(UUID id) {
        Optional<Poll> poll = pollRepository.findByUuid(id);
        if (poll.isPresent()) {
            measurementRepository.deleteByPollUuid(id);
            criteriaRepository.deleteByPollUuid(id);
            pollRepository.deleteByUuid(id);
            //TODO delete obsolete persons;
        }
    }

    public Optional<Poll> getPoll(UUID id) {
        return pollRepository.findByUuid(id);
    }

}
