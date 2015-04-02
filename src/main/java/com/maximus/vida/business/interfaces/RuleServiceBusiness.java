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
package com.maximus.vida.business.interfaces;


import com.psi.vida.business.vo.EligibilityInput;
import com.psi.vida.business.vo.EligibilityOutput;
import com.psi.vida.business.vo.MissingInformationInput;
import com.psi.vida.business.vo.MissingInformationOutput;
import com.psi.vida.business.vo.PremiumCalInput;
import com.psi.vida.business.vo.PremiumCalOutput;
import com.psi.vida.exception.RuleEngineDataException;

/**
 * RuleService Business Interface.
 */

public interface RuleServiceBusiness{
	
    /**
     * Determine Eligibility result based on the input AccountTO.
     * 
     * @param input EligibilityInput
     * @return EligibilityOutput
     * @throws RuleEngineDataException when account crashed rule engine
     * @pre EligibilityInput contains a valid AccountTO
     * @pre eligibility ruleset is deployed in JRUles engine
     * @post person whole eligibilityDenied = true does not have EligibilityResult created
	 * @post A list of EligibilityResultTO is returned
     */	
	public EligibilityOutput executeEligibility(EligibilityInput input) throws RuleEngineDataException;

    /**
     * Check Missing Information on a given account TO object.
     * 
     * @param in MissingInformationInput Containing AccountTO object
     * @return MissingInformationOutput - severity level and account TO with missing entries recorded inside
     * @throws RuleEngineDataException if the accoutn TO craches the rule engine
     * @pre MissingInformationInput contains a valid AccountTO
     * @pre MissingInformation ruleset is deployed in JRUles engine
	 * @post An errorCode is returned inside the MissingInformationOutput
	 * @post The account with missing entried populated in the person.missingInformation 
     */
	public MissingInformationOutput checkMissingInformation(MissingInformationInput in) throws RuleEngineDataException;
	
	/**
	 *  Calculate premium on an account based on the eligibility results of all applicants in the account.
	 * 
	 * @param in PremiumCalInput Containing Account TO and a List of EligibilityResultTO
	 * @return PremiumCalOutput - family premium amount
	 * @throws RuleEngineDataException if if the accoutn TO craches the rule engine
	 * @pre PremiumCalInput contains a valid AccountTO
	 * @pre PremiumCalInput contains a a list of Valid EligibilitiyResultTO
	 * @pre the list of EligibilityResultTO has FPL percentage calculated for use
     * @pre premiumCalculation ruleset is deployed in JRUles engine
     * @post The family-level premium amount (monthly) is calculated and returned in the PremiumOutput
     * @post The corresponding premium plan is calculated and returned in the PremiumOutput
	 */
	public PremiumCalOutput premiumCalculation(PremiumCalInput in) throws RuleEngineDataException;
	
    /**
     * Eligibility Screen based on the input AccountTO.
     * 
     * @param input EligibilityInput
     * @return EligibilityOutput
     * @throws RuleEngineDataException when account crashed rule engine
     * @pre EligibiliytInput contains a valid AccountTO
     * @pre eligibilityScreen ruleset is deployed in JRUles engine
     * @post if all children has insurance, family out of address or all children are overage, return PRESCREEN_REJECT_ACCOUNT
     * @post if none of the above condition match but family has state employee, return PRESCREEN_HAS_STATE_EMP
     * @post if none of the above condition match, return PRESCREEN_PASS
     * @post if a child's member status is PENGING_VERIFICATION and he is denied, change memberStatus to NOT_VERIFIED
     * @post if account status is PENDING_COMPLIANCE but now the account is rejected, chagne accuntStatus to COMPLIANT
     */
	public EligibilityOutput eligibilityScreen(EligibilityInput input) throws RuleEngineDataException;	
}
