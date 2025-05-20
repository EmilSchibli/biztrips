package ch.clip.trips.web;

import java.io.Serializable;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.clip.trips.repo.BusinessTripRepository;

@Controller
public class HomeController implements Serializable {
	private static Logger log = Logger.getLogger(HomeController.class.getSimpleName());
	private static final long serialVersionUID = 1L;

	@Autowired
	private BusinessTripRepository businessTripRepository;

	@PostConstruct
	public void init() {
		log.info("im init CustomerController");
	}

	@GetMapping("/home")
	@ResponseBody
	public String home(Model model) {

		// return something to check if the server is running
		log.info("im Home");
		return "Server läuft";

	}

	@GetMapping("/")
	@ResponseBody
	public String getHome(Model model) {
		log.info("im Home");
		return "simple";
	}

}
