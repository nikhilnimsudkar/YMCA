package com.ymca.web.model;

public class OtherPrograms {
private String name;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPrograms() {
	return programs;
}
public void setPrograms(String programs) {
	this.programs = programs;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
private String programs;
private String text;

private String defaultPromoCode;
public String getDefaultPromoCode() {
	return defaultPromoCode;
}
public void setDefaultPromoCode(String defaultPromoCode) {
	this.defaultPromoCode = defaultPromoCode;
}
}
