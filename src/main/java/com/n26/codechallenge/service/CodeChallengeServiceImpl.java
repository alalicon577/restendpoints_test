/**
 * 
 */
package com.n26.codechallenge.service;

import java.time.Duration;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.n26.codechallenge.data.TransactionTable;
import com.n26.codechallenge.model.Statistics;
import com.n26.codechallenge.model.Transaction;

/**
 * @author Arnel - June 2018
 *
 */
@Service
public class CodeChallengeServiceImpl implements CodeChallengeService {

	/*
	 * The assumption is that the transaction timestamp is already in UTC time zone.
	 * However, if this is not the case, I have also included the code to convert to
	 * UTC time zone
	 */
	@Override
	public HttpStatus addTransaction(Transaction transaction) {
		long transactionTime = transaction.getTimestamp();

		/*
		 * // if transactionTime is not in UTC format, the following code will be used.
		 * ZoneId currentZone = ZoneOffset.UTC; LocalDateTime localDateTimeDate =
		 * LocalDateTime.ofInstant(Instant.ofEpochMilli(transactionTime), currentZone);
		 * ZonedDateTime zdtDate = localDateTimeDate.atZone(currentZone); Instant start
		 * = Instant.ofEpochMilli(zdtDate.toInstant().toEpochMilli());
		 */

		Instant start = Instant.ofEpochMilli(transactionTime);/* transactionTime in UTC time zone */
		Instant end = Instant.now();/* already in UTC time zone */
		Duration elapsed = Duration.between(start, end);
		long duration = elapsed.getSeconds();

		/* check if transaction is older than 60 seconds */
		if (duration > 60) {/* return 204 */
			return HttpStatus.NO_CONTENT;
		} else {/* return 201 */

			/*
			 * I have used a SingleTon class to hold the data in memory without using a
			 * database
			 */
			TransactionTable transactionTable = TransactionTable.getInstance();
			transactionTable.getTransactionList().add(transaction);

			return HttpStatus.CREATED;
		}
	}

	/*
	 * At the beginning of the method, I get an Instant object to be used as single
	 * reference for the entire list at a point in time. Thus, making it
	 * consistent/constant all throughout the stream for each call to get the
	 * statistics.
	 */
	@Override
	public Statistics getStatistics() {
		Instant end = Instant.now();

		DoubleSummaryStatistics doubleSumStat = TransactionTable.getInstance().getTransactionList().stream()
				.filter(transaction -> {
					long transactionTime = transaction.getTimestamp();
					Instant start = Instant.ofEpochMilli(transactionTime);
					Duration elapsed = Duration.between(start, end);
					long duration = elapsed.getSeconds();
					return duration <= 60;
				}).mapToDouble(transaction -> transaction.getAmount()).summaryStatistics();

		Statistics statistics = new Statistics();
		if (doubleSumStat.getCount() > 0) {
			statistics.setCount(doubleSumStat.getCount());
			statistics.setAvg(doubleSumStat.getAverage());
			statistics.setMin(doubleSumStat.getMin());
			statistics.setMax(doubleSumStat.getMax());
			statistics.setSum(doubleSumStat.getSum());
		}
		return statistics;
	}

}
