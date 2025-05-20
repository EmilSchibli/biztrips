package ch.clip.trips.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.clip.trips.model.Meeting;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {
	List<Meeting> findByTitle(String title); 
}