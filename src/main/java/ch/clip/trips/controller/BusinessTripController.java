package ch.clip.trips.controller;

import ch.clip.trips.dto.BusinessTripDto;
import ch.clip.trips.dto.MeetingDto;
import ch.clip.trips.ex.BusinessTripNotFoundException;
import ch.clip.trips.model.BusinessTrip;
import ch.clip.trips.model.Meeting;
import ch.clip.trips.repo.BusinessTripRepository;
import ch.clip.trips.repo.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trips")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8082"})
public class BusinessTripController {

    @Autowired
    private BusinessTripRepository businessTripRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @GetMapping
    public ResponseEntity<List<BusinessTripDto>> getAllTrips() {
        List<BusinessTripDto> trips = businessTripRepository.findAll().stream()
                .map(this::convertTripToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessTripDto> getTripById(@PathVariable Long id) {
        return businessTripRepository.findById(id)
                .map(trip -> ResponseEntity.ok(convertTripToDto(trip)))
                .orElseThrow(() -> new BusinessTripNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<BusinessTripDto> createTrip(@RequestBody BusinessTripDto tripDto) {
        BusinessTrip trip = new BusinessTrip();
        trip.setTitle(tripDto.title());
        trip.setDescription(tripDto.description());
        trip.setStartTrip(tripDto.startTrip());
        trip.setEndTrip(tripDto.endTrip());
        // Meetings are not handled here to keep it simple, they can be added via the meeting endpoints

        BusinessTrip savedTrip = businessTripRepository.save(trip);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTrip.getId()).toUri();

        return ResponseEntity.created(location).body(convertTripToDto(savedTrip));
    }
    
    @GetMapping("/{tripId}/meetings")
    public ResponseEntity<List<MeetingDto>> getMeetingsForTrip(@PathVariable Long tripId) {
        if (!businessTripRepository.existsById(tripId)) {
            throw new BusinessTripNotFoundException(tripId);
        }
        List<MeetingDto> meetings = meetingRepository.findByBusinessTrip_Id(tripId).stream()
                .map(this::convertMeetingToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(meetings);
    }
    
    @PostMapping("/{tripId}/meetings")
    public ResponseEntity<MeetingDto> addMeetingToTrip(@PathVariable Long tripId, @RequestBody MeetingDto meetingDto) {
        BusinessTrip trip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new BusinessTripNotFoundException(tripId));

        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDto.title());
        meeting.setDescription(meetingDto.description());
        meeting.setBusinessTrip(trip);
        
        Meeting savedMeeting = meetingRepository.save(meeting);
        
        return ResponseEntity.created(URI.create("/v1/meetings/" + savedMeeting.getId())).body(convertMeetingToDto(savedMeeting));
    }

    @DeleteMapping("/{tripId}/meetings/{meetingId}")
    public ResponseEntity<Void> deleteMeetingFromTrip(@PathVariable Long tripId, @PathVariable Long meetingId) {
        if (!businessTripRepository.existsById(tripId)) {
            throw new BusinessTripNotFoundException(tripId);
        }
        if (!meetingRepository.existsById(meetingId)) {
            throw new ch.clip.trips.ex.MeetingNotFoundException(meetingId);
        }
        
        meetingRepository.deleteById(meetingId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        if (!businessTripRepository.existsById(id)) {
            throw new BusinessTripNotFoundException(id);
        }
        businessTripRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private BusinessTripDto convertTripToDto(BusinessTrip trip) {
        return new BusinessTripDto(
                trip.getId(),
                trip.getTitle(),
                trip.getDescription(),
                trip.getStartTrip(),
                trip.getEndTrip(),
                trip.getMeetings().stream().map(this::convertMeetingToDto).collect(Collectors.toList())
        );
    }

    private MeetingDto convertMeetingToDto(Meeting meeting) {
        return new MeetingDto(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getDescription(),
                meeting.getBusinessTrip().getId()
        );
    }
}
