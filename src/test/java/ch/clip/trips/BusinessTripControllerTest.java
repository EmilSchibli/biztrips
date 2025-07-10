package ch.clip.trips;

import com.fasterxml.jackson.databind.ObjectMapper;
import ch.clip.trips.model.BusinessTrip;
import ch.clip.trips.repo.BusinessTripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.clip.trips.dto.BusinessTripDto;
import ch.clip.trips.dto.MeetingDto;
import ch.clip.trips.model.Meeting;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BusinessTripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BusinessTripRepository businessTripRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private BusinessTrip sampleTrip;

    @BeforeEach
    void setUp() {
        businessTripRepository.deleteAll();
        sampleTrip = new BusinessTrip();
        sampleTrip.setTitle("Test City");
        sampleTrip.setDescription("A trip to Test City");
        sampleTrip.setStartTrip(LocalDateTime.of(2024, 1, 1, 9, 0));
        sampleTrip.setEndTrip(LocalDateTime.of(2024, 1, 5, 18, 0));
        sampleTrip = businessTripRepository.save(sampleTrip);
    }

    @Test
    void whenGetAllTrips_thenReturnsTripList() throws Exception {
        mockMvc.perform(get("/v1/trips"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Test City")));
    }

    @Test
    void whenGetTripById_andTripExists_thenReturnsTrip() throws Exception {
        mockMvc.perform(get("/v1/trips/" + sampleTrip.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(sampleTrip.getId().intValue())))
                .andExpect(jsonPath("$.title", is("Test City")));
    }

    @Test
    void whenGetTripById_andTripDoesNotExist_thenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/v1/trips/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateTrip_thenReturnsCreatedTrip() throws Exception {
        BusinessTripDto newTripDto = new BusinessTripDto(null, "New Destination", "A new trip", LocalDateTime.of(2025, 1, 1, 9, 0), LocalDateTime.of(2025, 1, 5, 18, 0), null);

        mockMvc.perform(post("/v1/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTripDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Destination")));
    }
    
    @Test
    void whenDeleteTrip_thenReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/v1/trips/" + sampleTrip.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/trips/" + sampleTrip.getId()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void whenGetMeetingsForTrip_thenReturnsMeetingList() throws Exception {
        // Setup: Add a meeting to the trip
        Meeting meeting = new Meeting("Test Meeting", "A test meeting", sampleTrip);
        
        List<Meeting> meetings = new java.util.ArrayList<>();
        meetings.add(meeting);
        sampleTrip.setMeetings(meetings);
        
        businessTripRepository.save(sampleTrip);

        mockMvc.perform(get("/v1/trips/" + sampleTrip.getId() + "/meetings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Test Meeting")));
    }

    @Test
    void whenAddMeetingToTrip_thenReturnsCreatedMeeting() throws Exception {
        MeetingDto newMeetingDto = new MeetingDto(null, "New Meeting Spot", "A new meeting", sampleTrip.getId());

        mockMvc.perform(post("/v1/trips/" + sampleTrip.getId() + "/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMeetingDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Meeting Spot")));
    }
} 