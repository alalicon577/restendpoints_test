package com.n26.codechallenge.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.codechallenge.data.TransactionTable;
import com.n26.codechallenge.model.Statistics;
import com.n26.codechallenge.model.Transaction;
import com.n26.codechallenge.service.CodeChallengeService;

/**
 * @author Arnel - June 2018
 * 
 *         I have used the following Java 8 features: 
 *         a. Instant 
 *         b. Duration 
 *         c. Stream 
 *         d. DoubleSummaryStatistics
 *         
 *         I have used a SingleTon class (TransactionTable) to hold the data in memory without using a database
 */
@RestController
@RequestMapping
public class CodeChallengeController {

	final static Logger log = Logger.getLogger(CodeChallengeController.class.getName());
	
	@Autowired
	CodeChallengeService codeChallengeService;
	
	@PostMapping("/transactions")
	public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction) {
		HttpStatus httpStatus = codeChallengeService.addTransaction(transaction);
		log.info("HttpStatus is " + httpStatus);
		return ResponseEntity.status(httpStatus).body(null);

	}

	@GetMapping("/statistics")
	public ResponseEntity<Statistics> getStatistics() {
		Statistics statistics = codeChallengeService.getStatistics();
		log.info("statistics is " + statistics);
		return new ResponseEntity<>(statistics, HttpStatus.OK);

	}

	/*This is just a test endpoint*/
	@GetMapping("/test")
	public ResponseEntity<String> testController() {

		return new ResponseEntity<>("good", HttpStatus.OK);
	}
}

