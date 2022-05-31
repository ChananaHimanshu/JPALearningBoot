package com.example.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class ChallengeSearch {
	private Long id;
	private String name;
	private String uri;
	private String description;
	private Date startDate;
	private Integer goal;
}
