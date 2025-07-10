package ch.clip.trips.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.clip.trips.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
	List<Meeting> findByBusinessTrip_Id(Long businessTripId);
}