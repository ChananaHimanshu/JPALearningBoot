package com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.entity.User;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;

	@NotEmpty(message = "first name must not be blank")
	private String firstName;

	@NotBlank(message = "last name must not be blank")
	private String lastName;

	@NotNull(message = "user name must not be blank")
	private String userName;

	private String password;

	public UserDTO(String firstName, String lastName, String userName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}

	public User to(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setUserName(userDTO.getUserName());
		return user;
	}
}
