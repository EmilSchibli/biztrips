package ch.clip.trips.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class BusinessTrip implements Serializable {

//	private static final long serialVersionUID = 67027563808382509L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private LocalDateTime startTrip;
	private LocalDateTime endTrip;

	@OneToMany(mappedBy = "businessTrip")
	@JsonManagedReference
	private List<Meeting> meetings;


	public BusinessTrip() {
		super();

	}

	public BusinessTrip(Long id, String title, String description, LocalDateTime startTrip, LocalDateTime endTrip) {
		this();
		this.id = id;
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

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
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

	@Override
	public String toString() {
		return "BusinessTrip [id=" + id + ", title=" + title + ", description=" + description + ", startTrip="
				+ startTrip + ", endTrip=" + endTrip + ", meetings=" + meetings + "]";
	}



}
