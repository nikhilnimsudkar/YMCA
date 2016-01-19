package com.ymca.web.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;

import com.ymca.model.Account;
import com.ymca.model.Signup;
import com.ymca.model.SignupPromotion;

public interface PromotionService {
	
	public String getPromotions(String itemContactMapStr, Account customer, String isAuto);
	
	public String getPromoMap(String itemDetailId, String contactId, Account customer, String isAuto, String isRecurring, String amountJSON, String selectedStartDate, List<Signup> cartSignups, String urlPromoCode, String urlContactId) throws ParseException;
	
	public boolean evaluatePromotionExpression(String sqlExpression, Signup signup, List<Signup> cartSignups);
	
	public Double computeMonthlyDiscountAmount(Double discountAmount, int recurringPeriod);
	
	public Double computeRemainingDiscountAmount(SignupPromotion signupPromotion, Double totalDiscountAmount, Double discountAmt);
	
	public HashMap<String, Object> getViewForPromoInURL(String userId, String i, String c, String contactId,  String pc, Model model);
}
