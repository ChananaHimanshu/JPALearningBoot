package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Challenge;
import com.example.service.ChallengeService;

@RestController
@RequestMapping("/challenge")
@Validated
public class ChallengeController {

	private ChallengeService challengeService;

	@Autowired
	public ChallengeController(ChallengeService challengeService) {
		this.challengeService = challengeService;
	}

	@GetMapping
	public List<Challenge> getChallenges(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String name, @RequestParam(required = false) String uri,
			@RequestParam(required = false) String description, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) Integer goal) {
		return challengeService.getChallenges(id, name, uri, description, startDate, goal);
	}
}
