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
package com.maximus.rule.internal;

import com.psi.vida.business.clearance.MissingInfoEnum;

/**
 * Class that keep the internal error code of missing information determination.
 *
 */
public class MissingInforInternalStatus {
	
	public boolean getAccountError() {
		return AccountError;
	}
	public void setAccountError(boolean accountError) {
		AccountError = accountError;
	}
	public boolean getELLiteError() {
		return ELLiteError;
	}
	public void setELLiteError(boolean eLLiteError) {
		ELLiteError = eLLiteError;
	}
	public boolean getMiscError() {
		return MiscError;
	}
	public void setMiscError(boolean miscError) {
		MiscError = miscError;
	}
	public boolean getEligibilityError() {
		return EligibilityError;
	}
	public void setEligibilityError(boolean eligibilityError) {
		EligibilityError = eligibilityError;
	}
	public MissingInforInternalStatus() {}
	
	private boolean AccountError = false;
	private boolean ELLiteError = false;
	private boolean MiscError = false;
	private boolean EligibilityError = false;
	
	public MissingInfoEnum getMissingInfoStatus(){
		if(AccountError){
			return MissingInfoEnum.MISSING_ACCOUNT_INFO;
		}else if(ELLiteError){
			return MissingInfoEnum.MISSING_EL_LITE_INFO;
		}else if(EligibilityError){
			return MissingInfoEnum.MISSING_ELIGIBILITY_INFO;
		}else if(MiscError){
			return MissingInfoEnum.MISSING_MISC_INFO;
		}else{
			return MissingInfoEnum.NONE;
		}
	}

}
