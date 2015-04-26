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
import org.junit.Test;

import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.to.MonthlyPremiumTO;
import com.psi.vida.business.to.PersonTO;
import com.psi.vida.business.vo.PremiumCalInput;
import com.psi.vida.business.vo.PremiumCalOutput;
import com.psi.vida.concept.ruleservice.util.RuleSetOOCTestDataUtil;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;


public class PremiumCalculationTestOOC extends AwVidaRuleEngineBaseTestCase{
	ILogger _logger = VidaLoggerFactory.getLogger(PremiumCalculationTestOOC.class);

    public PremiumCalculationTestOOC() {
    }
    
    @Test
    public void testPremiumCalculation_planA() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(1389.99d);
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
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	verifyPremiumResult(resultsFromRuleEngine, 0.0d, "A");
    }
    
    @Test
    public void testPremiumCalculation_planB() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(1589.99d);
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
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 11.0d, "B");
    }
    
    @Test
    public void testPremiumCalculation_planC() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(1709.99d);
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
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 22.0d, "C");
    }
    
    @Test
    public void testPremiumCalculation_planD() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(1909.99d);
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
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 24.0d, "D");
    }    
    
    @Test
    public void testPremiumCalculation_planE() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(2109.99d);
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
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 29.0d, "E");
    }

    
    @Test
    public void testPremiumCalculation_planF() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(2309.99d);
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
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 32.0d, "F");
    }
    
    @Test
    public void testPremiumCalculation_planG() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();

    	EligibilityResultTO erto = new EligibilityResultTO();
    	erto.setSchipSelectionType("XXI");
    	erto.setXxiIncome(2320.00d);
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
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 36.0d, "G");
    }
    
    @Test
    public void testPremiumCalculation_planF_twoChildren() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();
    	account = RuleSetOOCTestDataUtil.addChildToAccount(account, "CTWO", 8, RuleSetOOCTestDataUtil.c2Id);
    	
    	EligibilityResultTO erto1 = new EligibilityResultTO();
    	erto1.setSchipSelectionType("XXI");
    	erto1.setXxiIncome(2320.00d);
    	erto1.setXxiFamilyFpl(1000.00d);
    	erto1.setAccountRelationshipId(RuleSetOOCTestDataUtil.c1Id);
    	PersonTO applicant1 = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c1Id);
    	applicant1.setFirstName("C1");
    	erto1.setPerson(applicant1);
    	erto1.setSchipStatus("ELIGIBLE");
    	erto1.setEligibilityResultId(1111L);
 
    	EligibilityResultTO erto2 = new EligibilityResultTO();
    	erto2.setSchipSelectionType("XXI");
    	erto2.setXxiIncome(2320.00d);
    	erto2.setXxiFamilyFpl(1000.00d);
    	erto2.setAccountRelationshipId(RuleSetOOCTestDataUtil.c2Id);
    	PersonTO applicant2 = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c2Id);
    	applicant1.setFirstName("C2");
    	erto2.setPerson(applicant2);
    	erto2.setSchipStatus("ELIGIBLE");
    	erto2.setEligibilityResultId(1111L);
    	
    	PremiumCalInput input = new PremiumCalInput();
    	ArrayList<EligibilityResultTO> ls = new ArrayList<EligibilityResultTO>();
    	ls.add(erto1);
    	ls.add(erto2);
    	input.setAccount(account);
    	input.setResults(ls);
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 72.0d, "G");
    }
    
    @Test
    public void testPremiumCalculation_planF_twoChildrenFree() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();
    	account = RuleSetOOCTestDataUtil.addChildToAccount(account, "CTWO", 5, RuleSetOOCTestDataUtil.c2Id);
    	PersonTO child1 = RuleSetOOCTestDataUtil.findPersonFromAccount(account,  RuleSetOOCTestDataUtil.c1Id);
    	child1.setTribalMemberVerifiedFlag(true);
    	
    	EligibilityResultTO erto1 = new EligibilityResultTO();
    	erto1.setSchipSelectionType("XXI");
    	erto1.setXxiIncome(2310.00d);
    	erto1.setXxiFamilyFpl(1000.00d);
    	erto1.setAccountRelationshipId(RuleSetOOCTestDataUtil.c1Id);
    	PersonTO applicant1 = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c1Id);
    	erto1.setPerson(applicant1);
    	erto1.setSchipStatus("ELIGIBLE");
    	erto1.setEligibilityResultId(1111L);
 
    	EligibilityResultTO erto2 = new EligibilityResultTO();
    	erto2.setSchipSelectionType("XXI");
    	erto2.setXxiIncome(2310.00d);
    	erto2.setXxiFamilyFpl(1000.00d);
    	erto2.setAccountRelationshipId(RuleSetOOCTestDataUtil.c2Id);
    	PersonTO applicant2 = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c2Id);
    	erto2.setPerson(applicant2);
    	erto2.setSchipStatus("ELIGIBLE");
    	erto2.setEligibilityResultId(1111L);
    	
    	PremiumCalInput input = new PremiumCalInput();
    	ArrayList<EligibilityResultTO> ls = new ArrayList<EligibilityResultTO>();
    	ls.add(erto1);
    	ls.add(erto2);
    	input.setAccount(account);
    	input.setResults(ls);
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 00.0d, "F");
    }
    
    @Test
    public void testRetroPremium_planF_twoChildren() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();
    	account = RuleSetOOCTestDataUtil.addChildToAccount(account, "CTWO", 8, RuleSetOOCTestDataUtil.c2Id);
    	
    	EligibilityResultTO erto1 = new EligibilityResultTO();
    	erto1.setSchipSelectionType("XXI");
    	erto1.setXxiIncome(2310.00d);
    	erto1.setXxiFamilyFpl(1000.00d);
    	erto1.setAccountRelationshipId(RuleSetOOCTestDataUtil.c1Id);
    	PersonTO applicant1 = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c1Id);
    	applicant1.setFirstName("C1");
    	erto1.setPerson(applicant1);
    	erto1.setSchipStatus("ELIGIBLE");
    	erto1.setEligibilityResultId(1111L);
 
    	EligibilityResultTO erto2 = new EligibilityResultTO();
    	erto2.setSchipSelectionType("XXI");
    	erto2.setXxiIncome(2310.00d);
    	erto2.setXxiFamilyFpl(1000.00d);
    	erto2.setAccountRelationshipId(RuleSetOOCTestDataUtil.c2Id);
    	PersonTO applicant2 = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c2Id);
    	applicant1.setFirstName("C2");
    	erto2.setPerson(applicant2);
    	erto2.setSchipStatus("ELIGIBLE");
    	erto2.setEligibilityResultId(1111L);
    	
    	PremiumCalInput input = new PremiumCalInput();
    	ArrayList<EligibilityResultTO> ls = new ArrayList<EligibilityResultTO>();
    	ls.add(erto1);
    	ls.add(erto2);
    	input.setAccount(account);
    	input.setResults(ls);
    	ArrayList<Long> retroChildren = new ArrayList<Long>();
    	retroChildren.add(RuleSetOOCTestDataUtil.c1Id);
    	retroChildren.add(RuleSetOOCTestDataUtil.c2Id);
    	input.setChildrenEnrolled(retroChildren);
    	input.setPlanType("C");
    	
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 44.0d, "C");
    }
    
    @Test
    public void testRetroPremium_planF_oneChild() throws Exception{
    	AccountTO account = RuleSetOOCTestDataUtil.createBasedAccount();
    	
    	EligibilityResultTO erto1 = new EligibilityResultTO();
    	erto1.setSchipSelectionType("XXI");
    	erto1.setXxiIncome(2310.00d);
    	erto1.setXxiFamilyFpl(1000.00d);
    	erto1.setAccountRelationshipId(RuleSetOOCTestDataUtil.c1Id);
    	PersonTO applicant1 = RuleSetOOCTestDataUtil.findPersonFromAccount(account, RuleSetOOCTestDataUtil.c1Id);
    	applicant1.setFirstName("C1");
    	erto1.setPerson(applicant1);
    	erto1.setSchipStatus("ELIGIBLE");
    	erto1.setEligibilityResultId(1111L);
    	
    	PremiumCalInput input = new PremiumCalInput();
    	ArrayList<EligibilityResultTO> ls = new ArrayList<EligibilityResultTO>();
    	ls.add(erto1);
    	input.setAccount(account);
    	input.setResults(ls);
    	ArrayList<Long> retroChildren = new ArrayList<Long>();
    	retroChildren.add(RuleSetOOCTestDataUtil.c1Id);
    	input.setChildrenEnrolled(retroChildren);
    	input.setPlanType("F");
    	
    	PremiumCalOutput resultsFromRuleEngine = super.premiumCalculation(input);
    	
    	verifyPremiumResult(resultsFromRuleEngine, 32.0d, "F");
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
