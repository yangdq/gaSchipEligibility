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
package com.maximus.vida.concept.ruleservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;

import com.maximus.rule.internal.MissingInforInternalStatus;
import com.maximus.vida.business.interfaces.RuleServiceBusiness;
import com.maximus.vida.concept.ruleservice.util.DroolsRuleSessionFactory;
import com.psi.vida.business.eligibilitymanagement.processstatus.EligibilityPrescreenStatusEnum;
import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.EligibilityResultTO;
import com.psi.vida.business.vo.EligibilityInput;
import com.psi.vida.business.vo.EligibilityOutput;
import com.psi.vida.business.vo.MissingInformationInput;
import com.psi.vida.business.vo.MissingInformationOutput;
import com.psi.vida.business.vo.PremiumCalInput;
import com.psi.vida.business.vo.PremiumCalOutput;
import com.psi.vida.exception.RuleEngineDataException;
import com.psi.vida.log.ILogger;
import com.psi.vida.log.VidaLoggerFactory;

public class DroolsRuleServiceImpl implements RuleServiceBusiness {

	private static final ILogger _logger = VidaLoggerFactory.getLogger(DroolsRuleServiceImpl.class);
	private final static DroolsRuleSessionFactory sessionFactory = DroolsRuleSessionFactory.getInstance();
	
	private static final String RULEFLOW_PRESCREEN = "eligibilityScreen";
	private static final String RULEFLOW_FINANCIAL = "financialEligibility";
	private static final String RULEFLOW_MISSINGINFO = "missingInformation";
	private static final String RULEFLOW_PREMIUMCALCULATION = "premiumCalculation";	
	private static final String PRESCREEN_OUTPUT = "elgScreenOutput";
	private static final String FINANCIAL_OUTPUT = "eligibilityOutput";
	private static final String FINANCIAL_INPUT = "eligibilityInput";
	private static final String MISSINGINFO_OUTPUT = "missingInfoOutput";
	private static final String MISSINGINFO_ERRORCODE = "errorCode";
	private static final String PREMIUM_OUTPUT = "premiumOutput";
	private static final String DROOLS_INPUT = "input";
	
	private static final String PREVIEWONLY = "previewOnly";
	private static final String RETROCOVERAGE_PREMIUM = "retroPremium";
	
	
    
    public DroolsRuleServiceImpl() {
    }

     
    /**
     * Runs Eligibility Screen rules.
     * 
     * @param input EligibilityInput 
     * @return eligibility output including accountTO, results and liteSignature
     * @throws Exception
     */
    public EligibilityOutput eligibilityScreen(EligibilityInput input) throws RuleEngineDataException {
    	AccountTO account = input.getAccount();
    	EligibilityOutput output = new EligibilityOutput();
    	List<EligibilityResultTO> elgResults = new ArrayList<EligibilityResultTO>();
    	String liteSignature = EligibilityPrescreenStatusEnum.PRESCREEN_PASS.getValue();
    	KieSession ruleSession = sessionFactory.getRuleSession("eligibilityScreen").getSession();
    	
        output.setAccount(account);
        output.setResults(elgResults);
        output.setLiteSignature(liteSignature);
        ruleSession.setGlobal(PRESCREEN_OUTPUT, output);

        try{
        	ProcessInstance instance = ruleSession.startProcess(RULEFLOW_PRESCREEN);
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
    
    public EligibilityOutput executeEligibility(EligibilityInput input) throws RuleEngineDataException {
    	EligibilityOutput output = new EligibilityOutput();
    	List<EligibilityResultTO> elgResults = new ArrayList<EligibilityResultTO>();
    	KieSession ruleSession = sessionFactory.getRuleSession("financialEligibility").getSession();
    	
        output.setAccount(input.getAccount());
        output.setResults(elgResults);
        ruleSession.setGlobal(FINANCIAL_INPUT, input);
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
    
    public MissingInformationOutput checkMissingInformation(MissingInformationInput input) throws RuleEngineDataException {
    	MissingInformationOutput output = new MissingInformationOutput();
    	KieSession ruleSession = sessionFactory.getRuleSession("missingInformation").getSession();
    	
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
    
    public PremiumCalOutput premiumCalculation(PremiumCalInput input) throws RuleEngineDataException {
    	PremiumCalOutput output = new PremiumCalOutput();
    	KieSession ruleSession = sessionFactory.getRuleSession("premiumCalculation").getSession();
    
        ruleSession.setGlobal(PREMIUM_OUTPUT, output);
        ruleSession.setGlobal(DROOLS_INPUT, input);
        
        Map<String, Object> jbpmParams = new HashMap<String, Object>();
        jbpmParams.put(RETROCOVERAGE_PREMIUM, input.getPlanType() != null);
        try{
        	ruleSession.startProcess(RULEFLOW_PREMIUMCALCULATION, jbpmParams);
        	int ruleFired = ruleSession.fireAllRules();
        	_logger.debug("\nTotal Rules Fired " + ruleFired);
        }catch(Throwable t){
        	t.printStackTrace();
        }finally {
        	ruleSession.dispose();
        }
        return output;
    }

}
