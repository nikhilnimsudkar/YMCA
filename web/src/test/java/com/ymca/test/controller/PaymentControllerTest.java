package com.ymca.test.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ymca.CoreConfig;
import com.ymca.dao.DonationDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.model.Donation;
import com.ymca.model.Payment;
import com.ymca.model.PaymentMethod;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreConfig.class)
public class PaymentControllerTest {

	@Resource
	PaymentMethodDao paymentMethodDao;

	@Test
	public void getAll() {
		List<PaymentMethod> paymentList = paymentMethodDao.findAll();

		//System.out.println("Payment List Size " + paymentList.size());
	}
	
	@Test
	public void getPaymentMethodByAccountId() {
		List<PaymentMethod> payment = paymentMethodDao.getPaymentByAccountId(20L);
		////System.out.println("Payment List Size " + payment);	
	}
	
	
	
	@Test
	public void save() {
		PaymentMethod paymentVo = new PaymentMethod();
		//paymentVo.setAccountId(20L);
		paymentVo.setBillingAddressLine1("12345 Bldv");
		paymentVo.setBillingAddressLine2("123 Apartment");
		paymentVo.setBillingCity("San Jose");
		paymentVo.setBillingCountry("USA");
		paymentVo.setBillingState("CA");
		paymentVo.setCardNumber("1234567890");
		paymentVo.setExpirationMonth("07");
		paymentVo.setExpirationYear("24");
		paymentVo.setFullName("Jack Cooper");
		paymentVo.setIsPrimary(1);
		paymentVo.setId(45678L);
		paymentVo.setPaymentMethodType("Payment Type");
		paymentVo.setStatus(1);	
    	
		//Payment payment = paymentDao.saveAndFlush(paymentVo);
		////System.out.println(payment.getFullName());
	}
}
