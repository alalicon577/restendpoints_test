package com.n26.codechallenge.data;

import java.util.ArrayList;
import java.util.List;

import com.n26.codechallenge.model.Transaction;

public class TransactionTable {

	private static TransactionTable transactionTable;
	
	private TransactionTable() {
		
	}
	
	public static TransactionTable getInstance() {
		if(transactionTable==null) {
			transactionTable = new TransactionTable();
		}
		
		return transactionTable;
	}
	
	private List<Transaction> transactionList;

	public List<Transaction> getTransactionList() {
		if(transactionList==null) {
			transactionList = new ArrayList();
		}
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	
	
}
