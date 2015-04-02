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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.maximus.rule.internal.MissingInforInternalStatus;
import com.psi.vida.business.eligibilitymanagement.processstatus.EligibilityPrescreenStatusEnum;
import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.vo.EligibilityInput;
import com.psi.vida.business.vo.EligibilityOutput;
import com.psi.vida.business.vo.MissingInformationInput;
import com.psi.vida.business.vo.MissingInformationOutput;
import com.psi.vida.business.vo.PremiumCalInput;
import com.psi.vida.business.vo.PremiumCalOutput;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;

public class AwVidaRuleEngineBaseTestCase2{
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
	
    private static KieServices ks;
    private static KieContainer kContainer;
    
    public AwVidaRuleEngineBaseTestCase2() {
    	 ks = KieServices.Factory.get();
    	 kContainer = ks.getKieClasspathContainer();
    }
    
    protected static KieSession createKieSession(String sessionName){
    	KieSession kSession = kContainer.newKieSession(sessionName);
    	return kSession;
    }
    
    
    
    /**
     * Runs Eligibility Screen rules.
     * 
     * @param account AccountTO
     * @return eligibility output including accountTO, results and liteSignature
     * @throws Exception
     */
    protected static EligibilityOutput eligibilityScreen(EligibilityInput input) throws Exception {
    	AccountTO account = input.getAccount();
    	EligibilityOutput output = new EligibilityOutput();
    	List<EligibilityResultTO> elgResults = new ArrayList<EligibilityResultTO>();
    	String liteSignature = EligibilityPrescreenStatusEnum.PRESCREEN_PASS.getValue();
    	KieSession ruleSession = createKieSession("eligibilityScreen");
    	
        output.setAccount(account);
        output.setResults(elgResults);
        output.setLiteSignature(liteSignature);
        ruleSession.setGlobal(PRESCREEN_OUTPUT, output);

        try{
        	ruleSession.startProcess(RULEFLOW_PRESCREEN);
        	int ruleFired = ruleSession.fireAllRules();
        	_logger.debug("\nTotal Rules Fired " + ruleFired);
        	_logger.debug("\nEligibility Screen Results have " + elgResults.size() + " results");
        	_logger.debug("\nEligibility Screen Status: " + liteSignature);
        }catch(Throwable t){
        	t.printStackTrace();
        }finally {
        	ruleSession.dispose();
        }
        return output;
    }
    
    protected static EligibilityOutput determineEligibility(EligibilityInput input) throws Exception {
    	EligibilityOutput output = new EligibilityOutput();
    	List<EligibilityResultTO> elgResults = new ArrayList<EligibilityResultTO>();
    	KieSession ruleSession = createKieSession("eligibilityScreen");
    	
        output.setAccount(input.getAccount());
        output.setResults(elgResults);
        ruleSession.setGlobal(FINANCIAL_OUTPUT, output);
        
        Map<String, Object> jbpmParams = new HashMap<String, Object>();
        jbpmParams.put(PREVIEWONLY, input.getPreview());

        try{
        	ruleSession.startProcess(RULEFLOW_FINANCIAL, jbpmParams);
        	int ruleFired = ruleSession.fireAllRules();
        	_logger.debug("\nTotal Rules Fired " + ruleFired);
        	_logger.debug("\nFinancial Eligibility Results have " + elgResults.size() + " results");
        }catch(Throwable t){
        	t.printStackTrace();
        }finally {
        	ruleSession.dispose();
        }
        return output;
    }
    
    protected static MissingInformationOutput checkMissingInformation(MissingInformationInput input) throws Exception {
    	MissingInformationOutput output = new MissingInformationOutput();
    	KieSession ruleSession = createKieSession("missingInformation");
    	
        output.setAccount(input.getAccount());
        ruleSession.setGlobal(MISSINGINFO_OUTPUT, output);
        ruleSession.setGlobal(MISSINGINFO_ERRORCODE, new MissingInforInternalStatus());

        
        Map<String, Object> jbpmParams = new HashMap<String, Object>();
        jbpmParams.put(PREVIEWONLY, input.getPreview());
        
        try{
        	ruleSession.startProcess(RULEFLOW_MISSINGINFO, jbpmParams);
        	int ruleFired = ruleSession.fireAllRules();
        	_logger.debug("\nTotal Rules Fired " + ruleFired);
        }catch(Throwable t){
        	t.printStackTrace();
        }finally {
        	ruleSession.dispose();
        }
        return output;
    }
    
    protected static PremiumCalOutput calculatePremium(PremiumCalInput input) throws Exception {
    	PremiumCalOutput output = new PremiumCalOutput();
    	KieSession ruleSession = createKieSession("eligibilityScreen");
    
        ruleSession.setGlobal(PREMIUM_OUTPUT, output);
        ruleSession.setGlobal(DROOLS_INPUT, input);
        try{
        	ruleSession.startProcess(RULEFLOW_PREMIUMCALCULATION);
        	int ruleFired = ruleSession.fireAllRules();
        	_logger.debug("\nTotal Rules Fired " + ruleFired);
        }catch(Throwable t){
        	t.printStackTrace();
        }finally {
        	ruleSession.dispose();
        }
        return output;
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
