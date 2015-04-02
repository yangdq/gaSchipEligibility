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

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Drools Rule Session Factory.
 * 
 */
public class DroolsRuleSessionFactory implements RuleSessionFactory {
    private static DroolsRuleSessionFactory _instance = null;
    private static KieContainer kContainer;
    private static KieServices ks;

    private DroolsRuleSessionFactory() {
    	ks = KieServices.Factory.get();
    	kContainer = ks.getKieClasspathContainer();
    }

    public DroolsRuleSessionFactory(KieContainer container) {
        kContainer = container;
    }
    
    public static DroolsRuleSessionFactory getInstance() {
        if (_instance == null) {
            _instance = new DroolsRuleSessionFactory();
        }
        return _instance;
    }

    @Override
    public RuleSession getRuleSession(String sessionName) {
        final KieSession kieSession = kContainer.newKieSession(sessionName);
        // return Anonymous class of RuleSession type
        return new RuleSession() {
            private boolean disposed = false;

            @Override
            public KieSession getSession() {
                if (disposed){
                    throw new IllegalStateException("Can't get a disposed session");
                }
                return kieSession;
            }

            @Override
            public void dispose() {
                try {
                    if (!disposed) {
                    	kieSession.dispose();
                    }
                } finally {
                    disposed = true;
                }
            }
        };
    }
}

