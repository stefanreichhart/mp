package com.mp.persistence.repository;

import com.mp.MeasurementPointsLocalApplication;
import com.mp.persistence.model.Poll;
import com.mp.persistence.utils.ModelFactory;
import com.mp.utils.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementPointsLocalApplication.class)
@ActiveProfiles("test")
@Transactional
public class PollRepositoryTest {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private ModelFactory modelFactory;

    @Test
    public void basics() {
        pollRepository.findAll();
        pollRepository.count();
        pollRepository.deleteAll();
    }

    @Test
    public void savePollThrowsException() {
        assertThrows(DataIntegrityViolationException.class, () -> pollRepository.saveAndFlush(new Poll()));
    }

    @Test
    public void savePollWithInvalidTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> createAndSavePoll(null, null, null, null));
    }

    @Test
    public void savePollWithInvalidValidDateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> createAndSavePoll("title", null, LocalDateTime.now().plusSeconds(1), LocalDateTime.now().minusSeconds(1)));
    }

    @Test
    public void savePoll() {
        Assertions.assertSavedPoll(createAndSavePoll("title", null, null, null));
        Assertions.assertSavedPoll(createAndSavePoll("title", "description", null, null));
        Assertions.assertSavedPoll(createAndSavePoll("title", "description", LocalDateTime.now().minusSeconds(1), null));
        Assertions.assertSavedPoll(createAndSavePoll("title", "description", null, LocalDateTime.now()));
        Assertions.assertSavedPoll(createAndSavePoll("title", "description", LocalDateTime.now().minusSeconds(1), LocalDateTime.now()));
    }

    @Test
    public void saveDuplicatePoll() {
        Poll saved1 = createAndSavePoll("title", null, null, null);
        Assertions.assertSavedPoll(saved1);
        Poll saved2 = createAndSavePoll("title", null, null, null);
        Assertions.assertSavedPoll(saved2);
        assertNotEquals(saved1.getId(), saved2.getId());
        assertEquals(saved1.getTitle(), saved2.getTitle());
    }

    @Test
    public void findByIdNonExistingPoll() {
        Optional<Poll> poll = pollRepository.findByUuid(UUID.randomUUID());
        assertTrue(poll.isEmpty());
    }

    @Test
    public void findById() {
        Poll saved1 = createAndSavePoll("title", "description", null, null);
        Assertions.assertSavedPoll(saved1);
        Optional<Poll> poll = pollRepository.findByUuid(saved1.getId());
        assertTrue(poll.isPresent());
        Assertions.assertSavedPollEquals(saved1, poll.get());
    }

    private Poll createAndSavePoll(String title, String description, LocalDateTime validFrom, LocalDateTime validTo) {
        Poll poll = modelFactory.createPoll(title, description, validFrom, validTo);
        return pollRepository.saveAndFlush(poll);
    }

}
