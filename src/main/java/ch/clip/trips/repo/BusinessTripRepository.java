package ch.clip.trips.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.clip.trips.model.BusinessTrip;

public interface BusinessTripRepository extends JpaRepository<BusinessTrip, Long> {
	List<BusinessTrip> findByTitle(String title);
}
