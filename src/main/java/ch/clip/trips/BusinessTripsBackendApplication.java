package ch.clip.trips;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.clip.trips.model.BusinessTrip;
import ch.clip.trips.model.Meeting;
import ch.clip.trips.repo.BusinessTripRepository;
import ch.clip.trips.repo.MeetingRepository;

@SpringBootApplication
public class BusinessTripsBackendApplication {
	private static final Logger log = LoggerFactory.getLogger(BusinessTripsBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BusinessTripsBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(BusinessTripRepository businessTripRepository, MeetingRepository meetingRepository) {
		// https://spring.io/guides/gs/accessing-data-jpa/
		return (args) -> {

			
			// save a couple of BusinessTrips
			BusinessTrip bt01 = new BusinessTrip(1L, "BT01", "San Francisco World Trade Center on new Server/IOT/Client ",  LocalDateTime.of(2021, 2, 13, 9, 00),  LocalDateTime.of(2021, 2, 15, 16, 56));
			BusinessTrip bt02 = new BusinessTrip(2L, "BT02", "Santa Clara Halley on new Server/IOT/Client", LocalDateTime.of(2021, 6, 23, 9, 00),  LocalDateTime.of(2021, 6, 27, 16, 56));
			BusinessTrip bt03 = new BusinessTrip(3L, "BT03", "San Cose City Halley on Docker/IOT/Client", LocalDateTime.of(2021, 12, 13, 9, 00),  LocalDateTime.of(2021, 12, 15, 16, 56));

			businessTripRepository.save(bt01);
			businessTripRepository.save(bt02);
			businessTripRepository.save(bt03);

			// save a couple of meetings

			Meeting one = new Meeting(1L, "One Conference", "Key Note on One Conference", bt01);
			Meeting zero = new Meeting(2L, "Zero Conference", "Workshop Zero on One Conference", bt01);
			Meeting handsOn = new Meeting(3L, "One Conference", "HandsOn on One Conference", bt02);
			Meeting workOn = new Meeting(4L, "One Conference", "Key Note on One Conference", bt02);
			Meeting stayTuned = new Meeting(5L, "One Conference", "Key Note on One Conference", bt03);

			meetingRepository.save(one);
			meetingRepository.save(zero);
			meetingRepository.save(handsOn);
			meetingRepository.save(workOn);
			meetingRepository.save(stayTuned);
			
			
			// List<Trips> 
			
			List<BusinessTrip> wishTrips = new ArrayList<BusinessTrip>(); 

			wishTrips.add(bt01);
			wishTrips.add(bt02);
			
			


		};
	}

	
}
