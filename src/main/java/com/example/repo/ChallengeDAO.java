package com.example.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.domain.ChallengeSearch;
import com.example.entity.Challenge;

@Repository
public class ChallengeDAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<Challenge> searchByCriteria(ChallengeSearch challengeSearch) {
		System.out.println("-----------------------------------------");
		System.out.println(entityManager.getCriteriaBuilder());
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Challenge> query = criteriaBuilder.createQuery(Challenge.class);
		Root<Challenge> userRoot = query.from(Challenge.class);
		query.select(userRoot);
		
		 query.select(userRoot).where(criteriaBuilder.isNull(userRoot.get("name")));

		System.out.println("-------------PREDICATE-------------");
		List<Predicate> predicates = new ArrayList<>();
		if (challengeSearch.getName() != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("name"), challengeSearch.getName()));
		}
		if (challengeSearch.getUri() != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("uri"), challengeSearch.getUri()));
		}
		if (challengeSearch.getDescription() != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("description"), challengeSearch.getDescription()));
		}
		if (challengeSearch.getId() != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("id"), challengeSearch.getId()));
		}
		if (challengeSearch.getStartDate() != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("startDate"), challengeSearch.getStartDate()));
		}
		if (challengeSearch.getGoal() != null) {
			predicates.add(criteriaBuilder.equal(userRoot.get("goal"), challengeSearch.getGoal()));
		}
		query.where(predicates.toArray(new Predicate[0]));
		System.out.println("---------------NEw--------------");

		return entityManager.createQuery(query).getResultList();
	}
}
