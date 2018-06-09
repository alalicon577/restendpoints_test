package com.n26.codechallenge.model;

/**
 * @author barako
 *
 */
public class Statistics {

	/*
	 * total sum of transaction value in the last 60 seconds
	 */
	private double sum;
	
	/*
	 * average amount of transaction value in the last 60 seconds
	 */
	private double avg;
	
	/*
	 * single highest transaction value in the last 60 seconds
	 */
	private double max;
	
	/*
	 * single lowest transaction value in the last 60 seconds
	 */
	private double min;
	
	/*
	 * specifying the total number of transactions happened in the last 60 seconds
	 */
	private long count;
	
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		String stat = String.format("count=%s, sum=%s, avg=%s, min=%s, max=%s", 
				getCount(), getSum(), getAvg(), getMin(), getMax());
		
		return stat;
	}
}
