package ch.clip.trips.dto;

public record MeetingDto(
    Long id,
    String title,
    String description,
    Long businessTripId
) {} 