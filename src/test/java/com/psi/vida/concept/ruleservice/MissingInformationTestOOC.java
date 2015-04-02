/**
 * Copyright (c)2014 Maximus Inc. All rights reserved. This source code
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
/**
 * MissingInformation rule set OOC Unit Test.
 */


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.psi.vida.business.clearance.MissingInfoEnum;
import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.IncomeTO;
import com.psi.vida.business.to.MemberRoleTO;
import com.psi.vida.business.to.PersonTO;
import com.psi.vida.business.vo.MissingInformationInput;
import com.psi.vida.business.vo.MissingInformationOutput;
import com.psi.vida.concept.ruleservice.util.RuleSetOOCTestDataUtil;
import com.psi.vida.generatedenums.ListOfValuesUtil.MemberRoleEnum;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;

public class MissingInformationTestOOC extends AwVidaRuleEngineBaseTestCase{
	ILogger _logger = VidaLoggerFactory.getLogger(MissingInformationTestOOC.class);
	AccountTO account = null;
	MissingInformationInput input = new MissingInformationInput();

    public MissingInformationTestOOC() {
    }
    
    @Before
    public void setUp(){
    	account = RuleSetOOCTestDataUtil.createCompleteAccount();
    	input.setAccount(account);
    }
    
    @Test // Missing no information
    public void testNoInformationMissing() throws Exception{
    	String missingInfoResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.NONE.name(), missingInfoResult);
    }

    @Test // Missing Sex info is MISSING_MISC_INFO
    public void testMissingGender() throws Exception{
    	PersonTO child = findPersonByRole(account, MemberRoleEnum.ASPIRANT);
    	child.setGender(null);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_MISC_INFO.name(), missingInforResult);
    }
    
    @Test // Missing applicant should be return MISSING_ACCOUNT_INFO
    public void testMissingApplicant() throws Exception{
    	PersonTO child = findPersonByRole(account, MemberRoleEnum.ASPIRANT);
    	account.getPersons().remove(child);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_ACCOUNT_INFO.name(), missingInforResult);
    }
    
    @Test // Missing parent should be return MISSING_ACCOUNT_INFO
    public void testMissingParent() throws Exception{
    	PersonTO p1 = findPersonByRole(account, MemberRoleEnum.PRINCIPAL);
    	account.getPersons().remove(p1);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_ACCOUNT_INFO.name(), missingInforResult);
    }
    
    @Test // IncompleteIncome should be return MISSING_ELIGIBILITY_INFO
    public void testIncompleteIncome() throws Exception{
    	PersonTO p1 = findPersonByRole(account, MemberRoleEnum.PRINCIPAL);
    	IncomeTO income = p1.getIncomes().get(0);
    	income.setIncomeSubtype(null);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_ELIGIBILITY_INFO.name(), missingInforResult);
    }
    
    @Test // Missing DOB should be return MISSING_ELIGIBILITY_INFO
    public void testChildMissingDOB() throws Exception{
    	PersonTO p1 = findPersonByRole(account, MemberRoleEnum.ASPIRANT);
    	p1.setDateOfBirth(null);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_ELIGIBILITY_INFO.name(), missingInforResult);
    }
    
    @Test // Missing child has insurance should be return MISSING_ELIGIBILITY_INFO
    public void testChildMissingHasInsuranceAnswer() throws Exception{
    	PersonTO p1 = findPersonByRole(account, MemberRoleEnum.ASPIRANT);
    	p1.setHasInsuranceFlag(null);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_ELIGIBILITY_INFO.name(), missingInforResult);
    }
    
    @Test // Missing child cancel insurance should be return MISSING_ELIGIBILITY_INFO
    public void testChildMissingCancelInsuranceAnswer() throws Exception{
    	PersonTO c1 = findPersonByRole(account, MemberRoleEnum.ASPIRANT);
    	c1.setMemberLostInsuranceFlag(null);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_ELIGIBILITY_INFO.name(), missingInforResult);
    }
    
    @Test // Missing tax filer information should be return MISSING_ELIGIBILITY_INFO
    public void testChildMissingTaxInfo() throws Exception{
    	PersonTO p1 = findPersonByRole(account, MemberRoleEnum.ASPIRANT);
    	p1.setTaxFilerFlag(null);
    	String missingInforResult = super.checkMissingInformation(input).getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_ELIGIBILITY_INFO.name(), missingInforResult);
    }
    
    @Test // Missing parent first name should be return MISSING_ELIGIBILITY_INFO
    public void testParentFirstname() throws Exception{
    	PersonTO p1 = findPersonByRole(account, MemberRoleEnum.PRINCIPAL);
    	p1.setFirstName(null);
    	MissingInformationOutput output = super.checkMissingInformation(input);
    	String missingInforResult = output.getErrorCode();
    	Assert.assertEquals(MissingInfoEnum.MISSING_EL_LITE_INFO.name(), missingInforResult);
    }    
    
    private PersonTO findPersonByRole(AccountTO account, MemberRoleEnum memberRole){
    	for(PersonTO person : account.getPersons()){
    		MemberRoleTO role = person.getRoles().get(0);
    		if(memberRole.getValue().equals(role.getMemberRole())){
    			return person;
    		}
    	}
    	return null;
    }
}
