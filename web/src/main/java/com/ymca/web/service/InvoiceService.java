package com.ymca.web.service;

import java.util.Date;
import java.util.List;

import com.ymca.model.Invoice;
import com.ymca.model.Signup;


public interface InvoiceService {
	
	public String generateInvoiceNumber(Long accountId, Long signupId, Long payerId, Date nextBillDate);
	
	public Double getPastDueAmountBySignup(Signup signup);
	public List<Invoice> getAllInvoiceByBilldate(Signup signUp,Date billDate);
	public double getOpenBalanceForInvoice(Invoice invoice);
	public Date getInvoiceDraftDate(String draftCycle, Date dueDate);
	public void checkAndUpdateInvoiceDraftDate(Signup signup);
}
