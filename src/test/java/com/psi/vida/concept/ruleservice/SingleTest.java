/**
 * Copyright (c)2015 Policy Studies Inc. All rights reserved. This source code
 * constitutes confidential and proprietary information of Policy Studies Inc.
 * Access to it is restricted to PSI employees and agents authorized by PSI, and
 * then solely to the extent of such authorization. By accessing this source
 * code, you agree not to modify, copy, transfer, use, distribute, or delete it
 * except as authorized by PSI. Your failure to comply with these restrictions
 * may result in discipline, including termination of employment, may result in
 * severe civil and criminal penalties, and will be prosecuted to the maximum
 * extent possible under the law. 
 */
package com.psi.vida.concept.ruleservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.maximus.vida.business.interfaces.RuleServiceBusiness;
import com.maximus.vida.concept.ruleservice.impl.DroolsRuleServiceImpl;
import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.to.MonthlyPremiumTO;
import com.psi.vida.business.to.PersonTO;
import com.psi.vida.business.vo.PremiumCalInput;
import com.psi.vida.business.vo.PremiumCalOutput;
import com.psi.vida.concept.ruleservice.util.RuleSetOOCTestDataUtil;
import com.psi.vida.exception.RuleEngineDataException;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;


public class SingleTest{
	ILogger _logger = VidaLoggerFactory.getLogger(SingleTest.class);
	RuleServiceBusiness ruleService;

    public SingleTest() {
    	ruleService = new DroolsRuleServiceImpl();
    }
    
    public static void main(String[] args) throws Exception{
    	SingleTest instance = new SingleTest();
    	instance.run();
    }
    
    protected void run() throws RuleEngineDataException{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(1509.99d);
    	erto.setXxiFamilyFpl(1000.00d);
    	erto.setAccountRelationshipId(RuleSetOOCTestDataUtil.c1Id);
    	PersonTO applicant = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c1Id);
    	erto.setPerson(applicant);
    	erto.setSchipStatus("ELIGIBLE");
    	erto.setEligibilityResultId(1111L);
    	PremiumCalInput input = new PremiumCalInput();
    	ArrayList<EligibilityResultTO> ls = new ArrayList<EligibilityResultTO>();
    	ls.add(erto);
    	input.setAccount(account);
    	input.setResults(ls);
    	PremiumCalOutput resultsFromRuleEngine = ruleService.premiumCalculation(input);
    	verifyPremiumResult(resultsFromRuleEngine, 0.0d, "A");
    }
    
 
    
    
    private void verifyPremiumResult(PremiumCalOutput premiumOutput, Double amount, String plantype){
    	List<MonthlyPremiumTO> monthlyPremiums = premiumOutput.getMonthlyPremiums();
    	Assert.assertEquals("Should forcast for the next 24 months", 24, monthlyPremiums.size());
    	Double familyPremium = premiumOutput.getFamilyPremium();
    	Assert.assertEquals("Family premium should be " + amount, amount, familyPremium);
    	String planType = premiumOutput.getPremiumPlan();
    	Assert.assertEquals("Plan Type should be " + plantype, plantype, planType);    	
    	
    }
}
