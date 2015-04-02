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


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.to.EligibilityStatusReasonTO;
import com.psi.vida.business.to.PersonTO;
import com.psi.vida.business.vo.EligibilityInput;
import com.psi.vida.concept.ruleservice.util.RuleSetOOCTestDataUtil;
import com.psi.vida.generatedenums.ListOfValuesUtil.EarnedIncomeSubtypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.EligibilityStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.ExpenseEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.UnearnedIncomeSubtypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.VerificationStatusEnum;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;


public class AwEligibilityTestOOC extends AwVidaRuleEngineBaseTestCase{
	ILogger _logger = VidaLoggerFactory.getLogger(AwEligibilityTestOOC.class);
	AccountTO account = null;
	EligibilityInput input = new EligibilityInput();

    public AwEligibilityTestOOC() {
    }
    
    @Before
    public void setUp(){
    	account = RuleSetOOCTestDataUtil.createCompleteAccount();
    	input.setAccount(account);
    }

    @Test
    public void testIncomeCalculation() throws Exception {
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertTrue(result.getXxiIncome().equals(934.0d));
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    
    @Test
    public void testIncomeFiltering() throws Exception {
    	PersonTO parent = account.getPersons().get(0);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, EarnedIncomeSubtypeEnum.SELFEMPLOYMENT);
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, EarnedIncomeSubtypeEnum.OTHEREARNEDINCOME);
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 99.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.ANNUITYPAYMENTS);  
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 88.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.CHILDSUPPORTRECEIVED);
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 77.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.CONTRIBUTIONS); 
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 66.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.DISABILITY);  
    	// Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 55.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.LONGTERMDISABILITY); 
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.OTHERUNEARNEDINCOME); 
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 100.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.PENSIONRETIREMENTBENEFITS); 
    	//Ignored
    	RuleSetOOCTestDataUtil.addIncomeToPerson(parent, 44.0d, 
    			VerificationStatusEnum.VERIFIED, UnearnedIncomeSubtypeEnum.SUPPLEMENTALSECURITYINCOME);     	
    	
    	
    	List<EligibilityResultTO> results = super.determineEligibility(input).getResults();
    	Assert.assertEquals("One Result is Created", 1, results.size());
    	EligibilityResultTO result = results.get(0);
    	System.out.println(result.getXxiIncome());
    	Assert.assertTrue(result.getXxiIncome().equals(1334.0d));
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
    	Assert.assertTrue(result.getXxiIncome().equals(637.0d));
    	Assert.assertEquals(EligibilityStatusEnum.PENDINGELIGIBILITY.getValue(), result.getSchipStatus());
    	Assert.assertEquals("No Status Reason is associated to the result", 0, result.getStatusReasons().size());
    }
    

    

    
    /**
     * Get first status reason when there are more than one reason.
     * @param reasons multiple status reasons
     * @return
     */
    private String getStatusReason(Set<EligibilityStatusReasonTO> reasons) {
        java.util.Iterator it= reasons.iterator();
        if(it.hasNext()){
        	EligibilityStatusReasonTO reasonTO = (EligibilityStatusReasonTO) it.next();
        	return reasonTO.getStatusReason();
        }
        return new String();
    }
    
    public void genericTest(){
    	List<Integer> numbs = new ArrayList<Integer>(10);
    	
    	numbs.add(new Integer(10));
    	numbs.add(2);
    	process(numbs);
    }
    
    public  void process(List<? extends Number> list){
    	
    }
}
