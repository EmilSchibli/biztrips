package ch.clip.trips.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BusinessTripDto(
        Long id,
        String title,
        String description,
        LocalDateTime startTrip,
        LocalDateTime endTrip,
        List<MeetingDto> meetings
) {
} 