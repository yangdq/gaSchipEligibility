/**
 * Copyright (c) 2015 Policy Studies Inc. All rights reserved.
 * This source code constitutes confidential and proprietary
 * information of Policy Studies Inc. Access to it is restricted
 * to PSI employees and agents authorized by PSI, and then solely
 * to the extent of such authorization. By accessing this
 * source code, you agree not to modify, copy, transfer, use,
 * distribute, or delete it except as authorized by PSI. Your
 * failure to comply with these restrictions may result in
 * discipline, including termination of employment, may
 * result in severe civil and criminal penalties, and will
 * be prosecuted to the maximum extent possible under the law.
 */
package com.maximus.vida.concept.ruleservice.util;

import org.kie.api.runtime.KieSession;

/**
 * Interface to Rule Engine Execution.
 */
public interface RuleSession {
    public KieSession getSession();
    public void dispose();
}

//public class RuleSession {
//	private boolean disposed = false;
//	
//	public RuleSession(KieSession session){
//		this.session = session;
//	}
//	
//    private KieSession session;
//    
//    void dispose(){
//        try {
//            if (!disposed) {
//            	session.dispose();
//            }
//        }catch(Exception e){
//        	// Ignore any exception when dispose the KieSession.
//        }
//        finally {
//            disposed = true;
//        }    	
//    }
//
//	public KieSession getSession() {
//        if (disposed){
//            throw new IllegalStateException("Can't get a disposed session");
//        }
//        return session;
//	}
//
//	public void setSession(KieSession session) {
//		this.session = session;
//	}
//    
//    @Override
//    protected void finalize() throws Throwable {
//        dispose();
//    }
//}

