package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserDTO;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repo.UserRepository;
import com.example.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	private UserService userService;
	private UserRepository userRepository;

	@Autowired
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUser() {
		System.out.println("-----user controller getUser() calles--------");
		return userService.getUser();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable Long id) throws NotFoundException {
		return userService.getUser(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public User saveUser(@RequestBody User user) throws NotFoundException {
		return userService.saveUser(user);
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public List<Role> getRole(@RequestParam(required = false) Long id) throws NotFoundException {
		return userService.getRole(id);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<User> searchUser(@RequestParam(required = false) String firstname,
			@RequestParam(required = false) String lastname, @RequestParam(required = false) String username) {
		return userService.searchUser(firstname, lastname, username);
	}

	@RequestMapping(value = "/search/pagination", method = RequestMethod.GET)
	public Page<User> searchPaginatedUser(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam(required = false) String firstname, @RequestParam(required = false) String lastname,
			@RequestParam(required = false) String username) {
		return userService.searchPaginatedUser(page, size, firstname, lastname, username);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<String> addUser(@RequestBody @Valid User user) {
		userRepository.save(user);
		return ResponseEntity.ok("User data is valid");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(cv -> {
			errors.put("message", cv.getMessage());
			errors.put("path", (cv.getPropertyPath()).toString());
		});
		return errors;
	}

	@PostMapping(value = "/test")
	public List<User> testCriteria(@RequestParam(required = false) String firstname,
			@RequestParam(required = false) String lastname, @RequestParam(required = false) String username) {
		return userService.searchByCriteria(firstname, lastname, username);
	}
}
