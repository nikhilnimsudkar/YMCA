package com.ymca.web.enums;

public enum InteractionActivityTypeEnum {
	
	CancelEnrollment("Cancel Enrollment"),
	EventCancelled("Event Cancelled");
	
	public String value;
	
	private InteractionActivityTypeEnum(String str){
		value = str;
	}
	
	public String getValue(){
		return value;
	}
	
	public static InteractionActivityTypeEnum getEnumByString(String code){
		for(InteractionActivityTypeEnum e : InteractionActivityTypeEnum.values()){
			if(code.equals(e.value)) return e;
		}
		return null;
	}
}
