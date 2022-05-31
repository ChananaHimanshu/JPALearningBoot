package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.UserDTO;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repo.RoleRepository;
import com.example.repo.UserRepository;
import com.example.exception.NotFoundException;

@Service
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public List<User> getUser() {
		return this.userRepository.findAll();
	}

	public User getUserByUserName(String username) {
		return this.userRepository.getUserByUserName(username);
	}

	public User getUser(Long id) throws NotFoundException {
		Optional<User> user = this.userRepository.findById(id);
		if (!user.isPresent()) {
			throw new NotFoundException("User not found");
		}
		return user.get();

		// System.out.println("getting user -----------------getOne()");
		// User getUser = userRepository.getOne(id);
		//
		// System.out.println("First time record will come from databse");
		// Optional<User> studentResponse = userRepository.findById(id);
		//
		// System.out.println("Second time record will come from cache");
		// studentResponse = userRepository.findById(id);
		// System.out.println("Third time record will come from cache");
		// studentResponse = userRepository.findById(id);
		// System.out.println("Fourth time record will come from cache");
		// studentResponse = userRepository.findById(id);
		// User student = studentResponse.get();
		// return student;
	}

	public User saveUser(User user) throws NotFoundException {
		if (user.getId() != null) {
			User userDb = getUser(user.getId());
			userDb.setFirstName(user.getFirstName());
			userDb.setLastName(user.getLastName());
			if (user.getRoles() != null || !user.getRoles().isEmpty()) {
				List<Role> newRoles = new ArrayList<>();
				user.getRoles().stream().forEach(role -> {
					newRoles.add(roleRepository.getRoleByRoleName(role.getRoleName()));
				});
				userDb.setRoles(newRoles);
			}
			userDb = userRepository.save(userDb);
			return userDb;
		}
		if (user.getRoles() != null || !user.getRoles().isEmpty()) {
			List<Role> newRoles = new ArrayList<>();
			user.getRoles().stream().forEach(role -> {
				newRoles.add(roleRepository.getRoleByRoleName(role.getRoleName()));
			});
			user.setRoles(newRoles);
		}
		user = userRepository.save(user);
		return user;
	}

	public List<Role> getRole(Long id) throws NotFoundException {
		if (Objects.isNull(id)) {
			return this.roleRepository.findAll();
		}
		Optional<Role> role = this.roleRepository.findById(id);
		if (Objects.isNull(role) || !role.isPresent()) {
			throw new NotFoundException("Role not found");
		}
		return Arrays.asList(role.get());
	}

	public List<User> searchUser(String firstName, String lastName, String userName) {
		// ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withMatcher("firstName",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<User> example = Example.of(new User(firstName, lastName, userName), matcher);
		List<User> users = userRepository.findAll(example);
		return users;
	}

	public Page<User> searchPaginatedUser(int page, int size, String firstName, String lastName, String userName) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withMatcher("firstName",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<User> example = Example.of(new User(firstName, lastName, userName), matcher);
		Pageable pageable = PageRequest.of(page, size);
		Page<User> paginated = userRepository.findAll(example, pageable);

		return paginated;
	}

	public ResponseEntity<String> createUser(User user) {
		// User createdUser = userDTO.to(userDTO);
		user = userRepository.save(user);
		return ResponseEntity.ok("User data is valid");
		// return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	public List<User> searchByCriteria(String firstName, String lastName, String userName) {
		System.out.println("-----------------------------------------");
		System.out.println(entityManager.getCriteriaBuilder());
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> userRoot = query.from(User.class);
		query.select(userRoot);

		System.out.println("-------------PREDICATE-------------");
		List<Predicate> predicates = new ArrayList<>();
		if (firstName != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("firstName"), firstName));
		}
		if (lastName != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("lastName"), lastName));
		}
		if (userName != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("userName"), userName));
		}
		query.where(predicates.toArray(new Predicate[0]));
		System.out.println("---------------NEw--------------");
		
		
		return entityManager.createQuery(query).getResultList();
	}

}
