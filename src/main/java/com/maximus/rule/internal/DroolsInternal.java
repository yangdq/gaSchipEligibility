package com.maximus.rule.internal;

import java.util.Date;

public class DroolsInternal {
	private Integer retroCount = 0;
	private Double premiumAmount = 0.0d;
	private int lowestFPLPct = 2000;
	private Date currentMonth = new Date();

	public Integer getRetroCount() {
		return retroCount;
	}

	public void setRetroCount(Integer retroCount) {
		this.retroCount = retroCount;
	}

	public Double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public int getLowestFPLPct() {
		return lowestFPLPct;
	}

	public void setLowestFPLPct(int lowestFPLPct) {
		this.lowestFPLPct = lowestFPLPct;
	}

	public Date getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(Date currentMonth) {
		this.currentMonth = currentMonth;
	}	
	
	
}
