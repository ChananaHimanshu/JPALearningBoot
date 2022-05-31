package com.example.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.domain.ChallengeSearch;
import com.example.domain.ChallengeSearch.ChallengeSearchBuilder;
import com.example.dto.PaginatedListDTO;
import com.example.entity.Challenge;
import com.example.repo.ChallengeDAO;
import com.example.repo.ChallengeRepository;

@Service
public class ChallengeService {

	private ChallengeRepository challengeRepository;
	private ChallengeDAO challengeDAO;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	public ChallengeService(ChallengeRepository challengeRepository, ChallengeDAO challengeDAO) {
		this.challengeRepository = challengeRepository;
		this.challengeDAO = challengeDAO;
	}

	public List<Challenge> getChallenges(Long id, String name, String uri, String description, String startDate,
			Integer goal) {
		ChallengeSearch.ChallengeSearchBuilder builder = ChallengeSearch.builder();
		PaginatedListDTO<Challenge> paginatedListDTO = new PaginatedListDTO<>();

		if (id != null) {
			builder.id(id);
		}
		if (StringUtils.isEmpty(name)) {
			builder.name(name);
		}
		if (StringUtils.isEmpty(uri)) {
			builder.uri(uri);
		}
		if (StringUtils.isEmpty(description)) {
			builder.description(description);
		}
		if (startDate != null) {
			builder.startDate(getDateTime(startDate));
		}
		if (goal != null) {
			builder.goal(goal);
		}
		ChallengeSearch search = builder.build();
		// return challengeRepository.findAll();
		return challengeDAO.searchByCriteria(search);
	}

	public Date getDateTime(String timeStamp) {
		Date createdDate = null;
		if (timeStamp != null) {
			try {
				createdDate = new SimpleDateFormat("MM-dd-YYYY").parse(timeStamp);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new DateTimeException(e.getMessage());
			}
		}
		return createdDate;
	}

}
