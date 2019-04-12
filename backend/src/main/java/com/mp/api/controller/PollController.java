package com.mp.api.controller;

import com.mp.api.dto.PollDto;
import com.mp.api.dto.TableRowDto;
import com.mp.api.mapper.PollMapper;
import com.mp.api.service.PollService;
import com.mp.persistence.model.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private PollMapper pollMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PollDto> getPoll(@PathVariable("id") UUID id) {
        Optional<Poll> poll = pollService.getPoll(id);
        PollDto pollDto = pollMapper.mapPoll(poll);
        return new ResponseEntity<PollDto>(pollDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<PollDto> addPoll(@RequestBody PollDto dto) {
        Poll poll = pollService.addPoll(dto);
        PollDto pollDto = pollMapper.mapPoll(poll);
        return new ResponseEntity<PollDto>(pollDto, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<PollDto> modifyPoll(@RequestBody PollDto dto) {
        Poll poll = pollService.modifyPoll(dto);
        PollDto pollDto = pollMapper.mapPoll(poll);
        return new ResponseEntity<PollDto>(pollDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity removePoll(@PathVariable("id") UUID id) {
        pollService.removePoll(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}/data")
    public ResponseEntity<List<TableRowDto>> getData(
            @PathVariable("id") UUID id,
            @RequestParam("from") LocalDateTime from,
            @RequestParam("to") LocalDateTime to) {
        // TODO get table row data
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
