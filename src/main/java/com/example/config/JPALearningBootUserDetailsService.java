package com.example.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.entity.User;
import com.example.service.UserService;

@Component
public class JPALearningBootUserDetailsService implements UserDetailsService {

	private UserService userService;

	private HttpServletRequest request;

	@Autowired
	public JPALearningBootUserDetailsService(UserService userService, HttpServletRequest request) {
		this.userService = userService;
		this.request = request;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getUserByUserName(username);
		System.out.println("---------------------------------------USER----------------------------");
		System.out.println(user);
		System.out.println("------------------REQUEST------------------");
		System.out.println("username : " + request.getParameter("username"));
		System.out.println("grant_type : " + request.getParameter("grant_type"));
		System.out.println("Authorization : " + request.getHeader("Authorization"));
		if (user == null) {
			System.out.println("The email or password you entered is incorrect.");
			throw new UsernameNotFoundException("The email or password you entered is incorrect.");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		UserDetails userDetails = new JPALearningUserDetails(user.getId(), user.getUserName(), user.getPassword(),
				authorities);
		return userDetails;
	}

}
