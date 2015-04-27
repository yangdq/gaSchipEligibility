
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
package com.psi.vida.concept.ruleservice.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.psi.vida.business.to.AccountAddressTO;
import com.psi.vida.business.to.AccountTO;
import com.psi.vida.business.to.ExpenseTO;
import com.psi.vida.business.to.FamilyRelationshipTO;
import com.psi.vida.business.to.IncomeTO;
import com.psi.vida.business.to.MemberRoleTO;
import com.psi.vida.business.to.PartyContactInfoTO;
import com.psi.vida.business.to.PersonTO;
import com.psi.vida.generatedenums.LOV;
import com.psi.vida.generatedenums.ListOfValuesUtil.AccountStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.AddressTypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.ContactInfoTypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.EarnedIncomeSubtypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.ExpenseEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.FrequencyEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.IncomeTypeEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.MemberRoleEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.MemberStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.RelationshipToParentEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.UsCitizenshipStatusEnum;
import com.psi.vida.generatedenums.ListOfValuesUtil.VerificationStatusEnum;
import com.psi.vida.util.DateUtil;

public class RuleSetOOCTestDataUtil {
	
	public static Long p1Id = 1000000L;
	public static Long c1Id = 1000010L;
	public static Long c2Id = 1000011L;
	
	public static AccountTO createBasedAccount(){
		AccountTO account = new AccountTO();
		account.setAccountStatus(AccountStatusEnum.COMPLIANT.getValue());
		
		PersonTO p1 = new PersonTO();
		p1.setAccountRelationshipId(p1Id);
		p1.setPartyId(p1Id);
		MemberRoleTO p1Role = new MemberRoleTO();
		p1Role.setMemberRole(MemberRoleEnum.PRINCIPAL.getValue());
		p1.getRoles().add(p1Role);
		
		
		PersonTO c1 = new PersonTO();
		c1.setAccountRelationshipId(c1Id);
		c1.setPartyId(c1Id);
		c1.setDateOfBirth(DateUtil.addYears(DateUtil.currentDate(), -10));
		MemberRoleTO c1Role = new MemberRoleTO();
		c1Role.setMemberRole(MemberRoleEnum.ASPIRANT.getValue());
		c1.getRoles().add(c1Role);
		FamilyRelationshipTO fm1 = new FamilyRelationshipTO();
		fm1.setRelationshipType(RelationshipToParentEnum.CHILD.getValue());
		fm1.setToPersonTO(p1);
		c1.getRelationships().add(fm1);
			
		account.getPersons().add(p1);
		account.getPersons().add(c1);
	
		return account;
	}
	
	public static AccountTO createCompleteAccount(int chidlAge, double incomeAmt, String incomeSutType){
		AccountTO account = new AccountTO();
		account.setAccountStatus(AccountStatusEnum.COMPLIANT.getValue());
		account.setDeclaredStateEmployeeFlag(false);
		account.setDefaultLanguage("English");
		
		PersonTO p1 = new PersonTO();
		p1.setAccountRelationshipId(p1Id);
		p1.setPartyId(p1Id);
		MemberRoleTO p1Role = new MemberRoleTO();
		p1Role.setMemberRole(MemberRoleEnum.PRINCIPAL.getValue());
		p1.getRoles().add(p1Role);
		p1.setSsn("111111111");
		p1.setApplyingForBenefitsFlag(false);
		p1.setFirstName("PONE");
		p1.setLastName("OOC");
		p1.setTaxFilerFlag(false);
		p1.setTaxClaimsDependentFlag(false);
		p1.setTaxDependentFlag(false);
		p1.setLivesInPrimaryHousehold(true);
		p1.setDateOfBirth(new Date());
		p1.setGender("M");
		
		IncomeTO income = new IncomeTO();
		income.setDeclaredAmount(new BigDecimal(incomeAmt));
		income.setComputedMonthlyAmount(new BigDecimal(incomeAmt));
		income.setVerfiedmonthlyAmount(new BigDecimal(incomeAmt));
		income.setVerficationStatus(VerificationStatusEnum.VERIFIED.getValue());
		income.setDeclaredFrequency(FrequencyEnum.MONTHLY.getValue());
		income.setIncomeType(IncomeTypeEnum.EARNED.getValue());
		income.setIncomeSubtype(incomeSutType);
		p1.getIncomes().add(income);
		
		PartyContactInfoTO homePhone = new PartyContactInfoTO();
		homePhone.setContactType( ContactInfoTypeEnum.HOMEPHONE.getValue());
		homePhone.setContactInfo("123231212");
		p1.getPartyContacts().add(homePhone);
		
		PartyContactInfoTO emegPhone = new PartyContactInfoTO();
		emegPhone.setContactType( ContactInfoTypeEnum.EMERGENCYPHONE.getValue());
		emegPhone.setContactInfo("123231212");
		p1.getPartyContacts().add(emegPhone);
		
		PartyContactInfoTO workPhone = new PartyContactInfoTO();
		workPhone.setContactType( ContactInfoTypeEnum.WORKPHONE.getValue());
		workPhone.setContactInfo("123231212");
		p1.getPartyContacts().add(workPhone);
		
		PersonTO c1 = new PersonTO();
		c1.setAccountRelationshipId(c1Id);
		c1.setPartyId(c1Id);
		c1.setDateOfBirth(DateUtil.addYears(DateUtil.currentDate(), -chidlAge));
		MemberRoleTO c1Role = new MemberRoleTO();
		c1Role.setMemberRole(MemberRoleEnum.ASPIRANT.getValue());
		c1.getRoles().add(c1Role);
		FamilyRelationshipTO fm1 = new FamilyRelationshipTO();
		fm1.setRelationshipType(RelationshipToParentEnum.CHILD.getValue());
		fm1.setToPersonTO(p1);
		c1.getRelationships().add(fm1);
		
		c1.setSsn("111111112");
		c1.setApplyingForBenefitsFlag(true);
		c1.setFirstName("CONE");
		c1.setLastName("OOC");
		c1.setHasInsuranceFlag(false);
		c1.setMemberLostInsuranceFlag(false);
		c1.setTaxFilerFlag(false);
		c1.setTaxClaimsDependentFlag(false);
		c1.setTaxDependentFlag(false);
		c1.setLivesInPrimaryHousehold(true);
		c1.setGender("M");
		c1.setDateOfBirth(DateUtils.addYears(new Date(), -10));
		c1.setCitizen(true);
		c1.setDeclaredUsCitizenFlag(true);
		c1.setUsCitizenshipStatus(UsCitizenshipStatusEnum.CITIZEN.getValue());
		c1.setUsCitizenshipVerifiedFlag(true);
		c1.setUsCitizenshipVerifiedDate(new Date());
		c1.setIdentityVerifiedFlag(true);
		c1.setIdentityVerifiedDate(new Date());
		c1.setStatus(MemberStatusEnum.PENDINGVERIFICATION.getValue());
			
		account.getPersons().add(p1);
		account.getPersons().add(c1);
		
		AccountAddressTO address = new AccountAddressTO();
		address.setAddressType(AddressTypeEnum.HOMEADDRESS.getValue());
		address.setStreet("Street");
		address.setCity("city");
		address.setCounty("county");
		address.setPostalCode("77777");
		address.setServiceAreaId(10000L);
		address.setState("GA");
		account.getAccountAddresses().add(address);
	
		return account;		
	}
	
	public static AccountTO createCompleteAccount(){
		return createCompleteAccount(10, 1000.0d, EarnedIncomeSubtypeEnum.CURRENTEMPLOYER.getValue());
	}
	
	public static AccountTO addChildToAccount(AccountTO account, String firstName, int childAge, long arId){
		PersonTO c2 = new PersonTO();
		c2.setAccountRelationshipId(arId);
		c2.setPartyId(arId);
		c2.setFirstName(firstName);
		c2.setLastName("OOC");
		c2.setStatus(MemberStatusEnum.PENDINGVERIFICATION.getValue());
		c2.setDateOfBirth(DateUtil.addYears(DateUtil.currentDate(), -childAge));
		MemberRoleTO c2Role = new MemberRoleTO();
		c2Role.setMemberRole(MemberRoleEnum.ASPIRANT.getValue());
		c2.getRoles().add(c2Role);
		FamilyRelationshipTO fm2 = new FamilyRelationshipTO();
		fm2.setRelationshipType(RelationshipToParentEnum.CHILD.getValue());
		PersonTO principal = findPrincipalFromAccount(account);
		fm2.setToPersonTO(principal);
		c2.getRelationships().add(fm2);		
		
		account.getPersons().add(c2);
		return account;
	}
	
	public static void addIncomeToPerson(PersonTO person, double incomeAmt, VerificationStatusEnum ver, LOV incomeType, LOV incomeSubType){
		IncomeTO income = new IncomeTO();
		income.setDeclaredAmount(new BigDecimal(incomeAmt));
		income.setComputedMonthlyAmount(new BigDecimal(incomeAmt));
		if(VerificationStatusEnum.VERIFIED.equals(ver)){
			income.setVerfiedmonthlyAmount(new BigDecimal(incomeAmt));
			income.setVerficationStatus(VerificationStatusEnum.VERIFIED.getValue());
		}
		income.setDeclaredFrequency(FrequencyEnum.MONTHLY.getValue());
		income.setIncomeType(incomeType.getValue());
		income.setIncomeSubtype(incomeSubType.getValue());
		person.getIncomes().add(income);
	}
	
	public static void addExpenseToPerson(PersonTO person, double expenseAmt, ExpenseEnum expType){
		ExpenseTO expense = new ExpenseTO();
		expense.setDeclaredAmount(new BigDecimal(expenseAmt));
		expense.setComputedMonthlyAmount(new BigDecimal(expenseAmt));
		expense.setDeclaredFrequency(FrequencyEnum.MONTHLY.getValue());
		expense.setExpenseType(expType.getValue());
		person.getExpenses().add(expense);
	}

	
	public static PersonTO findPrincipalFromAccount(AccountTO account){
		for(PersonTO person : account.getPersons()){
			for(MemberRoleTO role : person.getRoles()){
				if(MemberRoleEnum.PRINCIPAL.getValue().equals(role.getMemberRole())){
					return person;
				}
			}
		}
		return null;
	}
	
	public static PersonTO findPersonFromAccount(AccountTO account, Long arId){
		for(PersonTO person : account.getPersons()){
			if(arId.equals(person.getAccountRelationshipId())){
				return person;
			}
		}
		return null;
	}
}
