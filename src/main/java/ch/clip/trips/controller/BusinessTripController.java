package ch.clip.trips.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.clip.trips.ex.TriptNotFoundException;
import ch.clip.trips.model.BusinessTrip;
import ch.clip.trips.repo.BusinessTripRepository;

@RestController
@RequestMapping("/v1")
public class BusinessTripController {

	@Autowired
	private BusinessTripRepository tripRepository;

//	ProductController(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}
 

	
	// Aggregate root
	@CrossOrigin(origins = "*")
	@GetMapping("/trips")
	// @RequestMapping(value = "/trips", method = RequestMethod.GET, produces =
	// "application/json")
	List<BusinessTrip> all() {
		return tripRepository.findAll();
	} 

	@PostMapping("/trips")
	// @RequestMapping(value = "/trips", method = RequestMethod.POST, produces =
	// "application/json", consumes = "appication/json")
	BusinessTrip newProduct(@RequestBody BusinessTrip newTrip) {
		return tripRepository.save(newTrip);
	}

	// single Item
	@GetMapping("/trips/{id}")
	BusinessTrip one(@PathVariable Long id) {
		return tripRepository.findById(id)
				.orElseThrow(() -> new TriptNotFoundException(id));
	}

	// with HATEOAS
//	@GetMapping("/products/{id}")
//	Resource<Product> one(@PathVariable Long id) {
//		Product product = productRepository.findById(id)
//				.orElseThrow(() -> new ProductNotFoundException(id));
//		return new Resource<>(product,
//				linkTo(methodOn(ProductController.class).one(id)).withSelfRel(),
//				linkTo(methodOn(ProductController.class).all()).withRel("products"));
//	}
	 
	
//	@PutMapping("/products/{id}")
//	BusinessTrip replaceProduct(@RequestBody BusinessTrip newProduct, @PathVariable Long id) {
//		return productRepository.findById(id).map(product -> {
//			product.setName(newProduct.getName());
//			product.setPrice(newProduct.getPrice());
//			product.setQuantity(newProduct.getQuantity());
//			return productRepository.save(product);
//
//		}).orElseGet(() -> {
//			newProduct.setId(id);
//			return productRepository.save(newProduct);
//		});
//	}

	@DeleteMapping("/products/{id}")
	void deleteProduct(@PathVariable Long id) {
		tripRepository.deleteById(id);
	}

}
