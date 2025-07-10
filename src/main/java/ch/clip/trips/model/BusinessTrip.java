package ch.clip.trips.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class BusinessTrip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private LocalDateTime startTrip;
	private LocalDateTime endTrip;

	@OneToMany(mappedBy = "businessTrip", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Meeting> meetings = new ArrayList<>();

	public BusinessTrip() {
	}

	public BusinessTrip(String title, String description, LocalDateTime startTrip, LocalDateTime endTrip) {
		this.title = title;
		this.description = description;
		this.startTrip = startTrip;
		this.endTrip = endTrip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartTrip() {
		return startTrip;
	}

	public void setStartTrip(LocalDateTime startTrip) {
		this.startTrip = startTrip;
	}

	public LocalDateTime getEndTrip() {
		return endTrip;
	}

	public void setEndTrip(LocalDateTime endTrip) {
		this.endTrip = endTrip;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BusinessTrip that = (BusinessTrip) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "BusinessTrip{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", startTrip=" + startTrip +
				", endTrip=" + endTrip +
				'}';
	}
}
