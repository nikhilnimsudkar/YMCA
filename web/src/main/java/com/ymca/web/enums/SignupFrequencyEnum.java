package com.ymca.web.enums;

public enum SignupFrequencyEnum {
	Weekly("Weekly"),
	SemiMonthly("Semi-Monthly"),
	Monthly("Monthly"),
	Quarterly("Quarterly"),
	Annual("Annual"),
	OneTime("One Time");
	
	public String value;
	
	private SignupFrequencyEnum(String str){
		value = str;
	}
	
	public String getValue(){
		return value;
	}
	
	public static SignupFrequencyEnum getEnumByString(String code){
		for(SignupFrequencyEnum e : SignupFrequencyEnum.values()){
			if(code.equals(e.value)) return e;
		}
		return null;
	}
}