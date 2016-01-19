package com.ymca.web.enums;

public enum ProductTypeEnum {
	EVENT("EVENT"),
	PROGRAM("PROGRAM"),
	CAMP("CAMP"),
	Memberships("Memberships"),
	MEMBERSHIP("MEMBERSHIP"),
	CHILDCARE("CHILD CARE"),
	FACILITY("FACILITY"),
	DONATION("Donations");
	
	public String value;
	
	private ProductTypeEnum(String str){
		value = str;
	}
	
	public String getValue(){
		return value;
	}
	
	public static ProductTypeEnum getEnumByString(String code){
		for(ProductTypeEnum e : ProductTypeEnum.values()){
			if(code.equals(e.value)) return e;
		}
		return null;
	}
}
