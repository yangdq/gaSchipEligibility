package com.maximus.rule.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DroolsInternal {
	private Integer retroCount = 0;
	private Double premiumAmount = 0.0d;
	private int lowestFPLPct = 2000;
	private Date currentMonth = new Date();
	private Boolean familyHasVerfieidIncome = false;
	private List<String> acceptedIncomeSubTypes = new ArrayList<String>();
	private List<String> acceptedExpenseTypes = new ArrayList<String>();

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

	public Boolean getFamilyHasVerfieidIncome() {
		return familyHasVerfieidIncome;
	}

	public void setFamilyHasVerfieidIncome(Boolean familyHasVerfieidIncome) {
		this.familyHasVerfieidIncome = familyHasVerfieidIncome;
	}

	public List<String> getAcceptedIncomeSubTypes() {
		return acceptedIncomeSubTypes;
	}

	public void setAcceptedIncomeSubTypes(List<String> acceptedIncomeSubTypes) {
		this.acceptedIncomeSubTypes = acceptedIncomeSubTypes;
	}

	public List<String> getAcceptedExpenseTypes() {
		return acceptedExpenseTypes;
	}

	public void setAcceptedExpenseTypes(List<String> acceptedExpenseTypes) {
		this.acceptedExpenseTypes = acceptedExpenseTypes;
	}	
	
	
	
}
