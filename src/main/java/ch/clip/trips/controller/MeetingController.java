package ch.clip.trips.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.clip.trips.ex.TriptNotFoundException;
import ch.clip.trips.model.Meeting;
import ch.clip.trips.repo.MeetingRepository;

@RestController
@RequestMapping("/v1")
public class MeetingController {
	private static final Logger log = LoggerFactory.getLogger(MeetingController.class);

	@Autowired
	private MeetingRepository meetingRepository;



	/**
	 * Method that returns the list of meetings in the current trip
	 *
	 * @return List of meetings
	 */
	//@CrossOrigin(origins ="http://localhost:3001")
	// @RequestMapping(value = "/meetings", method = RequestMethod.GET, produces =
	// "application/json")
	@GetMapping("/meetings")
	List<Meeting> allItems() {
		log.info("hello meetings");
		return (List<Meeting>) meetingRepository.findAll();

	}

	/**
	 * add a new Item to the list
	 *
	 * @param newItem Request object
	 * @return true/false
	 */
	@CrossOrigin(origins = "http://localhost:3001")
	@PostMapping("/meetings")
	Meeting newItem(@RequestBody Meeting newItem) {
		return meetingRepository.save(newItem);
	}

	// single Item
	@GetMapping("/meetings/{id}")
	Meeting one(@PathVariable Long id) {
		return meetingRepository.findById(id).orElseThrow(() -> new TriptNotFoundException(id));
	}

	@PutMapping("/meetings/{id}")
	Meeting replaceItem(@RequestBody Meeting newItem, @PathVariable Long id) {
		return meetingRepository.findById(id).map(item -> {
			item.setTitle(newItem.getTitle());
			item.setDescription(newItem.getDescription());
			return meetingRepository.save(item);

		}).orElseGet(() -> {
			newItem.setId(id);
			return meetingRepository.save(newItem);
		});
	}

	/**
	 * Method that deletes an item from the repo
	 *
	 * @param request Request object
	 * @return Status boolean
	 */
	@CrossOrigin(origins = "http://localhost:3001")
	@DeleteMapping("/meetings/{id}")
	void deleteItem(@PathVariable Long id) {
		meetingRepository.deleteById(id);
	}

	/**
	 * Method that empties the repo
	 *
	 * @return Status string
	 */
	@CrossOrigin(origins = "http://localhost:3001")
	@DeleteMapping("/meetings")
	void clearAll() {
		log.info("hello");
		meetingRepository.deleteAll();
	}

}
