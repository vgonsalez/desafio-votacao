package com.voting.voting_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	    "com.voting.voting_api.repository",
	    "com.voting.voting_api.service",
	    "com.voting.voting_api.model"
	})
	public class VotingApiApplication {
	    public static void main(String[] args) {
	        SpringApplication.run(VotingApiApplication.class, args);
	    }
	}