package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retieveUser (@PathVariable int id) {
		User user = service.findOne(id);
		if (user == null) {
		    throw new UserNotFountException("id-"+id);
		}
		// HATEOAS
		EntityModel<User> model = new EntityModel<>(user);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkTo.withRel("all-users"));
		return model;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser (@PathVariable int id) {
	    User user = service.deleteById(id);
        if (user == null) {
            throw new UserNotFountException("id-"+id);
        }
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser (@RequestBody @Valid User user) {
		User savedUser = service.save(user);
		URI location = ServletUriComponentsBuilder
		        .fromCurrentRequest()
		        .path("/{id}")
		        .buildAndExpand(savedUser.getId())
		        .toUri();
		
		return ResponseEntity.created(location).build();
	}
}
