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

/**
 * Generic Rule Session Factory Interface.
 *
 */
public interface RuleSessionFactory {
    RuleSession getRuleSession(String sessionName);

}

