/**
 * Copyright (c)2014 Maximus Inc. All rights reserved. This source code
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


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.psi.vida.business.eligibilitymanagement.processstatus.EligibilityPrescreenStatusEnum;
import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.to.EligibilityStatusReasonTO;
import com.psi.vida.business.to.InsuranceTO;
import com.psi.vida.business.to.PersonTO;
import com.psi.vida.business.vo.EligibilityInput;
import com.psi.vida.business.vo.EligibilityOutput;
import com.psi.vida.concept.ruleservice.util.RuleSetOOCTestDataUtil;
import com.psi.vida.generatedenums.ListOfValuesUtil.AccountStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.BorStateEmpMatchStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.EligibilityNotEligibleReasonEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.EligibilityStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.SmsStateEmpMatchStatusEnum;
import com.psi.vida.util.DateUtil;


public class EligibilityScreenTestOOC extends AwVidaRuleEngineBaseTestCase{
	//ILogger _logger = VidaLoggerFactory.getLogger(EligibilityScreenTestOOC.class);
	AccountTO account = null;
	EligibilityInput input = new EligibilityInput();

    public EligibilityScreenTestOOC() {
    }
    
    @Before
    public void setUp(){
    	account = RuleSetOOCTestDataUtil.createCompleteAccount();
    	input.setAccount(account);
    }

    @Test
    public void testPreScreenPass() throws Exception {
    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("Prescreen Pass", EligibilityPrescreenStatusEnum.PRESCREEN_PASS.getValue(), output.getLiteSignature());
    	Assert.assertTrue("Not result is created", results.isEmpty());
    }   
    
    @Test  //  State Employee
    public void testStateEmployeeNoImpact() throws Exception {
    	account.setDeclaredStateEmployeeFlag(true);
    	account.getPersons().get(0).setSmsStateEmpMatchStatus(SmsStateEmpMatchStatusEnum.MATCH.getValue());
    	account.getPersons().get(0).setBorStateEmpMatchStatus(BorStateEmpMatchStatusEnum.MATCH.getValue());
    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("State Employee Prescreen Pass", EligibilityPrescreenStatusEnum.PRESCREEN_PASS.getValue(), output.getLiteSignature());
    	Assert.assertTrue("Not result is created", results.isEmpty());
    }
    
    @Test  //  Has Insurance and has state employee
    public void testHasInsuranceWithStateEmployee() throws Exception {
    	account.getPersons().get(1).setHasInsuranceFlag(true);
    	account.setDeclaredStateEmployeeFlag(true);
    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("Has Insurance Prescreen Reject Account", EligibilityPrescreenStatusEnum.PRESCREEN_REJECT_ACCOUNT.getValue(), output.getLiteSignature());
    	Assert.assertEquals("One Result Created", 1, results.size());
    	EligibilityResultTO denialResult = results.get(0);
    	Assert.assertEquals(EligibilityStatusEnum.NOTELIGIBLE.getValue(), denialResult.getSchipStatus());
    	String statusReason = this.getStatusReason(denialResult.getStatusReasons());
    	Assert.assertEquals(EligibilityNotEligibleReasonEnum.HASOTHERINSURANCE.getValue(), statusReason);
    }
    
    @Test  //  Has Insurance but no declared State Employee
    public void testHasInsuranceNoEmployee() throws Exception {
       	account.getPersons().get(1).setHasInsuranceFlag(true);
    	account.setDeclaredStateEmployeeFlag(false);
    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("Has Insurance Prescreen Reject Account", EligibilityPrescreenStatusEnum.PRESCREEN_REJECT_ACCOUNT.getValue(), output.getLiteSignature());
    	Assert.assertEquals("One Result Created", 1, results.size());
    	EligibilityResultTO denialResult = results.get(0);
    	Assert.assertEquals(EligibilityStatusEnum.NOTELIGIBLE.getValue(), denialResult.getSchipStatus());
    	String statusReason = this.getStatusReason(denialResult.getStatusReasons());
    	Assert.assertEquals(EligibilityNotEligibleReasonEnum.HASOTHERINSURANCE.getValue(), statusReason);
    }  
    
    @Test  //  Has Insurance but the carrier name was waived as "GEORGIA MEDICAID" or "%well care%"
    public void testHasWaivedInsuranceType() throws Exception {
    	PersonTO child = account.getPersons().get(1);
       	child.setHasInsuranceFlag(true);
		InsuranceTO insurance = new InsuranceTO();
		insurance.setCarrierName("GEORGIA MEDICAID");
		child.getInsurances().add(insurance);
    	account.setDeclaredStateEmployeeFlag(false);

    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("Has Insurance Prescreen Reject Account", EligibilityPrescreenStatusEnum.PRESCREEN_REJECT_ACCOUNT.getValue(), output.getLiteSignature());
    	Assert.assertEquals("One Result Created", 1, results.size());
    	EligibilityResultTO denialResult = results.get(0);
    	Assert.assertEquals(EligibilityStatusEnum.NOTELIGIBLE.getValue(), denialResult.getSchipStatus());
    	String statusReason = this.getStatusReason(denialResult.getStatusReasons());
    	Assert.assertEquals(EligibilityNotEligibleReasonEnum.HASOTHERINSURANCE.getValue(), statusReason);
    }
    
    @Test  //  Out of State
    public void testOutOfState() throws Exception {
    	account.getAccountAddresses().get(0).setState("TX");
    	account.setAccountStatus(AccountStatusEnum.PENDINGCOMPLIANCE.getValue());
    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("Out of State Prescreen Reject Account", EligibilityPrescreenStatusEnum.PRESCREEN_REJECT_ACCOUNT.getValue(), output.getLiteSignature());
    	Assert.assertEquals("One Result Created", 1, results.size());
    	EligibilityResultTO denialResult = results.get(0);
    	Assert.assertEquals(EligibilityStatusEnum.NOTELIGIBLE.getValue(), denialResult.getSchipStatus());
    	String statusReason = this.getStatusReason(denialResult.getStatusReasons());
    	Assert.assertEquals(EligibilityNotEligibleReasonEnum.OUTOFSTATE.getValue(), statusReason);
    }
    
    @Test  //  Over Age
    public void testOverAge() throws Exception {
    	account.getPersons().get(1).setDateOfBirth(DateUtil.addYears(DateUtil.currentDate(), -20));
    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("Over Age Prescreen Reject Account", EligibilityPrescreenStatusEnum.PRESCREEN_REJECT_ACCOUNT.getValue(), output.getLiteSignature());
    	Assert.assertEquals("One Result Created", 1, results.size());
    	EligibilityResultTO denialResult = results.get(0);
    	Assert.assertEquals(EligibilityStatusEnum.NOTELIGIBLE.getValue(), denialResult.getSchipStatus());
    	String statusReason = this.getStatusReason(denialResult.getStatusReasons());
    	Assert.assertEquals(EligibilityNotEligibleReasonEnum.OVERAGE.getValue(), statusReason);
    }
    
    @Test  //  Not All Children are Denied
    public void testPartialDenial() throws Exception {
    	account = RuleSetOOCTestDataUtil.addChildToAccount(account, "CTWO", 20, RuleSetOOCTestDataUtil.c2Id);
    	EligibilityOutput output = super.eligibilityScreen(input);
    	List<EligibilityResultTO> results = output.getResults();
    	Assert.assertEquals("Partial Over Age Prescreen Pass", EligibilityPrescreenStatusEnum.PRESCREEN_PASS.getValue(), output.getLiteSignature());
    	Assert.assertEquals("One Result Created", 1, results.size());
    	EligibilityResultTO denialResult = results.get(0);
    	Assert.assertEquals(EligibilityStatusEnum.NOTELIGIBLE.getValue(), denialResult.getSchipStatus());
    	String statusReason = this.getStatusReason(denialResult.getStatusReasons());
    	Assert.assertEquals(EligibilityNotEligibleReasonEnum.OVERAGE.getValue(), statusReason);
    }
    
    /**
     * Get first status reason when there are more than one reason.
     * @param reasons multiple status reasons
     * @return
     */
    private String getStatusReason(Set<EligibilityStatusReasonTO> reasons) {
        Iterator<EligibilityStatusReasonTO> it= reasons.iterator();
        if(it.hasNext()){
        	EligibilityStatusReasonTO reasonTO = it.next();
        	return reasonTO.getStatusReason();
        }
        return new String();
    }

}
