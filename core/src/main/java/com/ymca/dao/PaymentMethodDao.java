package com.ymca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.Account;
import com.ymca.model.PaymentMethod;

public interface PaymentMethodDao extends GenericDao<PaymentMethod, Long> {
    
	//
	public final static String FIND_PAYMENT_METHOD_BY_PAYMENT_ID = "SELECT p.fullName, p.cardNumber, p.id, c.accountId  FROM PaymentMethod p inner join p.customer c WHERE p.id = :paymentId";
	
	@Query("SELECT p from PaymentMethod p inner join p.customer c where c.accountId=?1")
 	List<PaymentMethod> getPaymentByAccountId(Long accountId);
	//LEFT JOIN Account a ON p.accountId=a.accountId 
	PaymentMethod getByFullName(String name);
	
	PaymentMethod getByOldToken(String oldToken);
	
	//@Query(FIND_PAYMENT_METHOD_BY_PAYMENT_ID)
	//SELECT p.fullName, p.cardNumber, p.paymentId, c.accountId  from PaymentMethod p inner join p.customer c where p.paymentId=?1")
	@Query("SELECT p  from PaymentMethod p inner join p.customer c where p.id=?1")
	List<PaymentMethod> getPaymentMethodByPaymentId(Long paymentId);
	
	/*@Query("SELECT p  from PaymentMethod p inner join p.customer c where p.token=?1")
	List<PaymentMethod> getPaymentMethodByToken(String token);*/
	
	PaymentMethod getPaymentMethodByTokenNumber(String tokenNumber);
	
	//@Query("SELECT p FROM PaymentMethod p WHERE p.orderNumber = ?1")
	PaymentMethod getByOrderNumber(String orderNumber);
	
	@Query("SELECT p.portalStatus, p.paymentMethodType, p.isPrimary, p.cardNumber, p.expirationMonth, p.expirationYear, p.tokenNumber, p.nickName, p.fullName, p.id from PaymentMethod p inner join p.customer c where c.accountId=?1 and p.portalStatus = 'ACTIVE' and p.paymentMethodType = 'CREDIT' ")
 	List<PaymentMethod> getCreditCardInfoByAccountId(Long accountId);
	
	@Query("SELECT p.tokenNumber, p.isPrimary, p.cardNumber, p.nickName, p.paymentMethodType, p.expirationMonth, p.expirationYear, p.portalStatus, p.id from PaymentMethod p inner join p.customer c where c.accountId=?1 and ((p.portalStatus = 'ACTIVE' and p.paymentMethodType = 'CREDIT')  or  (p.portalStatus IN ('ACTIVE', 'VP') and p.paymentMethodType = 'ACH')) ")
 	List<PaymentMethod> getPaymentMethodListForAccountID(Long accountId);
	
	@Query("SELECT p.transId,  p.bankRoutingNumber, p.fullName, p.paymentMethodType,  p.checkingAccountNumber, p.driversLicenseNumber, p.stateOfDL, p.phoneNumber, p.nickName, p.portalStatus,  p.tokenNumber , p.id, p.isPrimary from PaymentMethod p inner join p.customer c where c.accountId=?1 and p.portalStatus = 'ACTIVE' and p.paymentMethodType = 'EFT' ")
 	List<PaymentMethod> getACHInfoByAccountId(Long accountId);
	
	@Query("SELECT p.id FROM PaymentMethod p inner join p.customer c WHERE c.accountId=?1 AND p.portalStatus IN ('ACTIVE', 'INACTIVE') and p.paymentMethodType = 'CREDIT' AND p.cardNumber=?2 AND p.expirationMonth=?3 AND p.expirationYear=?4 ")
 	Long isCardAlreadyExist(Long accountId, String cardNumber, String expMonth, String expYear);
	
	@Query("SELECT p.id FROM PaymentMethod p inner join p.customer c WHERE c.accountId=?1 AND p.portalStatus IN ('ACTIVE', 'INACTIVE', 'VP') and p.paymentMethodType = 'ACH' AND p.cardNumber=?2 ")
 	Long isBankInfoAlreadyExist(Long accountId, String bankAccountNumber);
	
	@Query("SELECT p FROM PaymentMethod p WHERE p.customer = ?1 and ((p.portalStatus = 'Active' and p.paymentMethodType = 'CREDIT')  or  (p.portalStatus IN ('Active') and p.paymentMethodType = 'ACH')) ")
 	List<PaymentMethod> getPaymentMethodListForAccount(Account account);

	PaymentMethod findById(Long id);
	
}
