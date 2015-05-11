/**
 * Copyright (c)2015 Maximus Inc. All rights reserved. This source code
 * constitutes confidential and proprietary information of Maximus Inc.
 * Access to it is restricted to Maximus employees and agents authorized by Maximus, and
 * then solely to the extent of such authorization. By accessing this source
 * code, you agree not to modify, copy, transfer, use, distribute, or delete it
 * except as authorized by Maxiumus. Your failure to comply with these restrictions
 * may result in discipline, including termination of employment, may result in
 * severe civil and criminal penalties, and will be prosecuted to the maximum
 * extent possible under the law. 
 */
package com.psi.vida.concept.ruleservice;


import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.to.PersonTO;
import com.psi.vida.business.vo.EligibilityInput;
import com.psi.vida.concept.ruleservice.util.RuleSetOOCTestDataUtil;
import com.psi.vida.generatedenums.ListOfValuesUtil.AccountStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.EarnedIncomeSubtypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.EligibilityStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.ExpenseEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.IncomeTypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.UnearnedIncomeSubtypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.VerificationStatusEnum;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;


public class AwEligibilityTestOOC extends AwVidaRuleEngineBaseTestCase{
	ILogger _logger = VidaLoggerFactory.getLogger(AwEligibilityTestOOC.class);
	AccountTO account = null;
	EligibilityInput input = new EligibilityInput();
	
	protected static final double comparisonDelta = 0.00001d;

    public AwEligibilityTestOOC() {
    }
    
    @Before
    public void setUp(){
    	account = RuleSetOOCTestDataUtil.createCompleteAccount();
    	input.setAccount(account);
    	input.setMonthlyBaseAmount(new BigDecimal(634.17d));
    	input.setMonthlyPerPersonAmount(new BigDecimal(346.67d));
    }
    
    @Test
    public void testNoFinancialReady() throws Exception {
    	account.setAccountStatus(AccountStatusEnum.PENDINGCOMPLIANCE.getValue());
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }    
    
    @Test
    public void testIncomeCalculation() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 833.00d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(1833.00d, result.getXxiIncome().doubleValue(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testIncomeMdcdDeduction() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 832.99d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);    	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(1765.99d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testIncomeMdcd_NoDeduction() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 833.10d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);    	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(1833.10d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }    
    
    @Test
    public void testIncomeSchipDeduction() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 2346.00d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);    	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(3279.00d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testIncomeSchip_NoDeduction() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 2346.01d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);    	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(3346.01d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.NOTELIGIBLE.getValue(), result.getSchipStatus());
    	Assert.assertEquals("One Status Reason is associated to the result", 1, result.getStatusReasons().size());
    }    
    
    @Test
    public void testIncomeFiltering() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.OTHEREARNEDINCOME);
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 99.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.ANNUITYPAYMENTS);  
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 88.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.CHILDSUPPORTRECEIVED);
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 77.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.CONTRIBUTIONS); 
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 66.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.DISABILITY);  
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 55.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.LONGTERMDISABILITY); 
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.OTHERUNEARNEDINCOME); 
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.PENSIONRETIREMENTBENEFITS); 
    	//Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 44.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.SUPPLEMENTALSECURITYINCOME);     	
    	
    	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(1400.0d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testExpenseDeduction() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	// The following expenses are deducted from income total
    	RuleSetOOCTestDataUtil.addExpenseToPerson(parent, 100.0d, ExpenseEnum.ALIMONY);
    	RuleSetOOCTestDataUtil.addExpenseToPerson(parent, 99.0d, ExpenseEnum.STUDENTLOANINTEREST);
    	RuleSetOOCTestDataUtil.addExpenseToPerson(parent, 98.0d, ExpenseEnum.OTHER);
    	// No deduction on this expense type
    	RuleSetOOCTestDataUtil.addExpenseToPerson(parent, 97.0d, ExpenseEnum.CHILDSUPPORT);
    	// No deduction on this expense type
    	RuleSetOOCTestDataUtil.addExpenseToPerson(parent, 96.0d, ExpenseEnum.DAYCARE);
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(703.0d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testDependentIncomeFiltering_exclude() throws Exception {
    	PersonTO child = account.getPersons().get(1);
    	
    	// Ignored since earned income annual amt < 6200 and unearned income annual amt < 1000
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 100.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 80.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.SOCIALSECURITY);  
    	 	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(1000.0d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testDependentIncomeFiltering_Include() throws Exception {
    	PersonTO child = account.getPersons().get(1);
    	
    	// Included.  Although earned income annual amt < 6200, unearned income annual amt > 1000
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 100.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 90.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.SOCIALSECURITY);  
    	 	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(1190.0d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testDependentIncomeFiltering_excludeRSDI() throws Exception {
    	PersonTO child = account.getPersons().get(1);
    	
    	// Excluded.  Earned income annual amt < 6200, unearned income annual amt > 1000 but it is RSDI so ignore it
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 100.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 1000.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.RSDI);  
    	 	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertEquals(1000.0d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    
    @Test
    public void testDependentIncomeFiltering_includeRSDI() throws Exception {
    	PersonTO child = account.getPersons().get(1);
    	
    	// Included.  Earned income annual amt > 6200, unearned is RSDI so ignore it in threshold calculation
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 520.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.EARNED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(child, 80.0d, 
    			VerificationStatusEnum.VERIFIED, IncomeTypeEnum.UNEARNED, UnearnedIncomeSubtypeEnum.RSDI);  
    	 	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	// RSDI amount is included in the final calculation, even if RSDI amt is excluded in the threshold calculation
    	Assert.assertEquals(1600.0d, result.getXxiIncome(), comparisonDelta);
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
}
