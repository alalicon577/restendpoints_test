package com.n26.codechallenge.service;

import org.springframework.http.HttpStatus;

import com.n26.codechallenge.model.Statistics;
import com.n26.codechallenge.model.Transaction;

public interface CodeChallengeService {

	HttpStatus addTransaction(Transaction transaction);
	Statistics getStatistics();
}
