package com.serene.job.util;

public enum PaymentTypeEnum {
	//CreditMemoFAWaiver("CMFAWaiver"),
	CreditMemoFAWaiver("Credit Memo/FA Waiver"),
	CreditMemoWaiver("CMWaiver"),
	CreditMemoRefund("CMRefund"),
	CreditMemoWriteOff("CMWriteOff"),
	Debit("Debit"),
	NSF("NSF"),
	ChargeBack("ChargeBack"),
	None("None"),
	Cash("Cash"),
	Check("Check"),
	CreditMemoADJ("CMADJ"),
	Stock("Stock"),
	New("New"),
	Payment("Payment"),
	Recurring("Recurring"),
	NonRecurring("Non-Recurring");
	
	public String value;
	
	private PaymentTypeEnum(String str){
		value = str;
	}
	
	public String getValue(){
		return value;
	}
	
	public static PaymentTypeEnum getEnumByString(String code){
		for(PaymentTypeEnum e : PaymentTypeEnum.values()){
			if(code.equals(e.value)) return e;
		}
		return null;
	}
}
