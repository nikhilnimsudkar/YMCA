package com.ymca.web.enums;

public enum SSOTokenWebTagsEnum {
	RESULT("result"),
	VALUE("Value"),
	USERPERSONDETAILS("UserPersonDetails"),
	EMAILADDRESS("EmailAddress"),
	USERNAME("Username"),
	ROLES("Roles");	
	
	
	public String value;
	
	private SSOTokenWebTagsEnum(String str){
		value = str;
	}
	
	public String getValue(){
		return value;
	}
}
