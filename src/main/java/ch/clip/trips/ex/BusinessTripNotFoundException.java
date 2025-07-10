package ch.clip.trips.ex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BusinessTripNotFoundException extends RuntimeException {
    public BusinessTripNotFoundException(Long id) {
        super("Could not find business trip " + id);
    }
} 