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


import java.util.List;

import com.maximus.vida.business.interfaces.RuleServiceBusiness;
import com.maximus.vida.concept.ruleservice.impl.DroolsRuleServiceImpl;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.vo.EligibilityInput;
import com.psi.vida.business.vo.EligibilityOutput;
import com.psi.vida.business.vo.MissingInformationInput;
import com.psi.vida.business.vo.MissingInformationOutput;
import com.psi.vida.business.vo.PremiumCalInput;
import com.psi.vida.business.vo.PremiumCalOutput;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;

public class AwVidaRuleEngineBaseTestCase{
	static ILogger _logger = VidaLoggerFactory.getLogger(AwVidaRuleEngineBaseTestCase.class);
	static String OUTPUT_ELIGIBILITY_RESULT = "eligibilityResults";
	static String INOUTPUT_ACCOUNT = "account";
	static String OUTPUT_SCREEN_STATUS = "screenStatus";
	static String RULEFLOW_PRESCREEN = "eligibilityScreen";
	static String RULEFLOW_FINANCIAL = "financialEligibility";
	static String RULEFLOW_MISSINGINFO = "missingInformation";
	static String RULEFLOW_PREMIUMCALCULATION = "premiumCalculation";	
	static String PRESCREEN_OUTPUT = "elgScreenOutput";
	static String FINANCIAL_OUTPUT = "financialOutput";
	static String MISSINGINFO_OUTPUT = "missingInfoOutput";
	static String MISSINGINFO_ERRORCODE = "errorCode";
	static String PREMIUM_OUTPUT = "premiumOutput";
	static String DROOLS_INPUT = "input";
	static String PREVIEWONLY = "previewOnly";
    
	protected RuleServiceBusiness ruleService = null;
    
    public AwVidaRuleEngineBaseTestCase() {
    	 ruleService = new DroolsRuleServiceImpl();
    }

    /**
     * Runs Eligibility Screen rules.
     * 
     * @param account AccountTO
     * @return eligibility output including accountTO, results and liteSignature
     * @throws Exception
     */
    protected EligibilityOutput eligibilityScreen(EligibilityInput input) throws Exception {
    	
    	return ruleService.eligibilityScreen(input);
    }
    
    protected EligibilityOutput determineEligibility(EligibilityInput input) throws Exception {
    	return ruleService.executeEligibility(input);

    }
    
    protected MissingInformationOutput checkMissingInformation(MissingInformationInput input) throws Exception {
    	return ruleService.checkMissingInformation(input);
    }
    
    protected PremiumCalOutput premiumCalculation(PremiumCalInput input) throws Exception {
    	return ruleService.premiumCalculation(input);
    }
    
    /**
     * Locate the eligibiilty from a list of resutls that belongs to one person.
     * 
     * @param results - list of results
     * @param firstName - the person's first name
     * @return EligibilityResultTO
     */
    protected static EligibilityResultTO findResult(List<EligibilityResultTO> results, 
			String firstName)
    {
    	EligibilityResultTO result = null;
    	for(EligibilityResultTO res : results){
    		if(firstName.equals(res.getPerson().getFirstName())){
    			result = res;
    			break;
    		}
    	}
    	return result;	
    }    
}
