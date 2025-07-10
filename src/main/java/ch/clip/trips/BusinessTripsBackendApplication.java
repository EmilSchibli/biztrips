package ch.clip.trips;

import ch.clip.trips.model.BusinessTrip;
import ch.clip.trips.model.Meeting;
import ch.clip.trips.repo.BusinessTripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class BusinessTripsBackendApplication {
	private static final Logger log = LoggerFactory.getLogger(BusinessTripsBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BusinessTripsBackendApplication.class, args);
	}

	@Bean
	public static CommandLineRunner initData(BusinessTripRepository tripRepository) {
		return args -> {
			log.info("Generating test data...");

			// Trip 1
			BusinessTrip trip1 = new BusinessTrip("Q2 Tech-Konferenz", "Teilnahme an der jährlichen Technologiekonferenz in Berlin.", LocalDateTime.of(2024, 4, 15, 9, 0), LocalDateTime.of(2024, 4, 18, 17, 0));
			Meeting meeting1_1 = new Meeting("Keynote: Zukunft der KI", "Eröffnungsvortrag der Konferenz", trip1);
			Meeting meeting1_2 = new Meeting("Workshop: Cloud Native", "Praktischer Workshop zu Kubernetes", trip1);
			trip1.setMeetings(Arrays.asList(meeting1_1, meeting1_2));
			tripRepository.save(trip1);

			// Trip 2
			BusinessTrip trip2 = new BusinessTrip("Kundenbesuch in Hamburg", "Treffen mit dem Kunden ABC GmbH.", LocalDateTime.of(2024, 5, 20, 10, 0), LocalDateTime.of(2024, 5, 21, 15, 0));
			Meeting meeting2_1 = new Meeting("Projekt-Review", "Besprechung des aktuellen Projektstatus", trip2);
			Meeting meeting2_2 = new Meeting("Vertragsverhandlung", "Finale Verhandlung für den neuen Vertrag", trip2);
			trip2.setMeetings(Arrays.asList(meeting2_1, meeting2_2));
			tripRepository.save(trip2);

			// Trip 3
			BusinessTrip trip3 = new BusinessTrip("Team-Offsite München", "Strategie-Workshop mit dem Entwicklungsteam.", LocalDateTime.of(2024, 6, 10, 11, 0), LocalDateTime.of(2024, 6, 12, 16, 0));
			Meeting meeting3_1 = new Meeting("Jahresrückblick 2023", "Analyse der letztjährigen Ergebnisse", trip3);
			Meeting meeting3_2 = new Meeting("Roadmap-Planung 2025", "Planung der Produkt-Roadmap für das nächste Jahr", trip3);
			Meeting meeting3_3 = new Meeting("Team-Building Event", "Gemeinsames Abendessen und Bowling", trip3);
			trip3.setMeetings(Arrays.asList(meeting3_1, meeting3_2, meeting3_3));
			tripRepository.save(trip3);

			// ... Add 7 more realistic trips ...

			BusinessTrip trip4 = new BusinessTrip("Sales-Messe Frankfurt", "Präsentation unserer neuen Produkte auf der Sales-Messe.", LocalDateTime.of(2024, 7, 1, 8, 0), LocalDateTime.of(2024, 7, 3, 18, 0));
			Meeting meeting4_1 = new Meeting("Standaufbau", "Vorbereitung des Messestandes", trip4);
			Meeting meeting4_2 = new Meeting("Produkt-Demos", "Live-Demonstrationen für Besucher", trip4);
			trip4.setMeetings(Arrays.asList(meeting4_1, meeting4_2));
			tripRepository.save(trip4);

			BusinessTrip trip5 = new BusinessTrip("Management-Meeting Zürich", "Quartalsmeeting des Managements.", LocalDateTime.of(2024, 8, 5, 9, 0), LocalDateTime.of(2024, 8, 5, 17, 0));
			Meeting meeting5_1 = new Meeting("Finanzergebnisse Q2", "Präsentation der Quartalszahlen", trip5);
			trip5.setMeetings(Arrays.asList(meeting5_1));
			tripRepository.save(trip5);

			BusinessTrip trip6 = new BusinessTrip("Schulung in London", "Weiterbildung im Bereich Projektmanagement.", LocalDateTime.of(2024, 9, 2, 9, 0), LocalDateTime.of(2024, 9, 6, 16, 0));
			Meeting meeting6_1 = new Meeting("Agile Methoden", "Kurs über Scrum und Kanban", trip6);
			Meeting meeting6_2 = new Meeting("Zertifizierungsprüfung", "Abschlussprüfung für das Zertifikat", trip6);
			trip6.setMeetings(Arrays.asList(meeting6_1, meeting6_2));
			tripRepository.save(trip6);

			BusinessTrip trip7 = new BusinessTrip("Partner-Akquise Wien", "Treffen mit potenziellen neuen Partnern.", LocalDateTime.of(2024, 10, 14, 10, 0), LocalDateTime.of(2024, 10, 15, 18, 0));
			Meeting meeting7_1 = new Meeting("Vorstellung der Firma", "Präsentation unseres Unternehmens", trip7);
			trip7.setMeetings(Arrays.asList(meeting7_1));
			tripRepository.save(trip7);

			BusinessTrip trip8 = new BusinessTrip("Entwickler-Hackathon", "Interner Hackathon in Köln.", LocalDateTime.of(2024, 11, 8, 9, 0), LocalDateTime.of(2024, 11, 9, 20, 0));
			Meeting meeting8_1 = new Meeting("Ideen-Pitches", "Vorstellung der Hackathon-Ideen", trip8);
			Meeting meeting8_2 = new Meeting("Abschlusspräsentationen", "Vorstellung der Ergebnisse", trip8);
			trip8.setMeetings(Arrays.asList(meeting8_1, meeting8_2));
			tripRepository.save(trip8);

			BusinessTrip trip9 = new BusinessTrip("Messebesuch in Paris", "Besuch der internationalen Branchenmesse.", LocalDateTime.of(2025, 1, 20, 9, 0), LocalDateTime.of(2025, 1, 22, 17, 0));
			Meeting meeting9_1 = new Meeting("Networking-Event", "Treffen mit Branchenkollegen", trip9);
			trip9.setMeetings(Arrays.asList(meeting9_1));
			tripRepository.save(trip9);

			BusinessTrip trip10 = new BusinessTrip("Jahresabschlussfeier", "Feier zum Jahresende in Düsseldorf.", LocalDateTime.of(2024, 12, 20, 18, 0), LocalDateTime.of(2024, 12, 20, 23, 0));
			Meeting meeting10_1 = new Meeting("Abendessen", "Gemeinsames Essen im Restaurant", trip10);
			trip10.setMeetings(Arrays.asList(meeting10_1));
			tripRepository.save(trip10);

			log.info("Test data generated: {} business trips.", tripRepository.count());
		};
	}
}
