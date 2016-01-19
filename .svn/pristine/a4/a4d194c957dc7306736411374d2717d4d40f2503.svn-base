package com.ymca.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPFactory;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import com.serene.bootstrap.WebServiceBootStrap;
import com.serene.ws.binding.CustomSoapParser;
import com.serene.ws.exception.WebServiceException;
import com.serene.ws.service.impl.GenericWebServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebServiceBootStrap.class)
public class HallPassIntegrationTest {
	
	@Resource(name="genericWebServiceImpl")
	GenericWebServiceImpl genericWebService;
	
	@Resource
	private CustomSoapParser customSoapParser ;  

	@Test
	public void IsSOR() {
		//GenericWebServiceImpl g = new GenericWebServiceImpl();
		try {
			 String strXML = new String(""
			      +"<IsSOR xmlns=\"http://HallPassId.com/SORData/\">"
			         +"<FirstName>Govind</FirstName>"
			         +"<LastName>Patwa</LastName>"
			         +"<DOB>1978-12-11</DOB>"
			      +"</IsSOR>"
			   );
			
			 ClientInterceptor[] interceptors = new ClientInterceptor[1];
			 /*Wss4jSecurityInterceptor wsSecurityInterceptor = new Wss4jSecurityInterceptor();
			 wsSecurityInterceptor.setSecurementUsername("YmCaSV");
			 wsSecurityInterceptor.setSecurementPassword("yMCa6445");
			 wsSecurityInterceptor.setSecurementActions("UsernameToken");
			 //wsSecurityInterceptor.setSecurementPasswordType(WSConstants.PASSWORD_TEXT);
			 interceptors[0] = wsSecurityInterceptor;
			 genericWebService.setInterceptors(interceptors);*/
			 genericWebService.invoke(strXML, "http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/IsSOR");
			//System.out.println(k);
		} catch (WebServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void IsSORBySpringWs() {
		//GenericWebServiceImpl g = new GenericWebServiceImpl();
		try {
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("FirstName", "John");
		 	data.put("LastName", "Johnson");
		 	data.put("DOB", "1984-10-15");
			payLoad.put("IsSOR", data);
	        
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent("YmCaSV");
	        password.setTextContent("yMCa6445");
	      //commented to resolve compilation issues
	        //Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/IsSOR",usernameToken);
	        //System.out.println(out);
	        //System.out.println(out.get("IsSORResult"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void FetchSORDetailsBySpringWs() {
		//GenericWebServiceImpl g = new GenericWebServiceImpl();
		try {
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("FirstName", "Steven");
		 	data.put("LastName", "Levine");
		 	data.put("DOB", "1976-06-19");
		 	//data.put("FirstName", "John");
		 	//data.put("LastName", "Johnson");
		 	//data.put("DOB", "1984-10-15");
		 	data.put("PageCount", "1");
		 	data.put("PageSize", "10");
			payLoad.put("FetchSORDetails", data);
	        
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent("YmCaSV");
	        password.setTextContent("yMCa6445");
	        //commented to resolve compilation issues
	       /* Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/FetchSORDetails",usernameToken);
	        
	        List<Map> sexOffenderDetails = (List<Map>) out.get("FetchSORDetailsResult");
	        System.out.println(sexOffenderDetails);
	        
	        for(Map e: sexOffenderDetails){
	        	
	        	System.out.println(e);
	        	for(Map s: (List<Map>) e.get("SORResData")){
	        		System.out.println(s.get("SexOffenderIdNumber"));
	        	}
	        }*/
	        
	        
	        /*
	        System.out.println(out.size());
	        String value = out.get("FetchSORDetailsResult").toString();
	        value = value.substring(1, value.length()-1);
	        System.out.println(value);
	        value = value.replaceAll("=", ":");
	        System.out.println(value);
	        */
	        /*
	        value = value.substring(13, value.length()-2);
	        System.out.println(value);
	        
	        String value1 = StringUtils.substringBetween(value, "{", "}");
	        
	        System.out.println(value1);*/
	        
	        /*
	        for(int index = value.indexOf("SORResData"); index >= 0; index = value.indexOf("SORResData", index + 1)){
	        	    System.out.println(index);
	        }*/
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void loopingEx() {
		String data = "{SORResData=[{CaseCount=2, MiddleName=M,    SexOffenderIdNumber=429505, DOB=19760619, SexOffenderDatabaseId=NY, Suffix=, Image=http://secure.hallpassid.com/SORPhotos/NY/11152.JPG, FirstName=STEVEN, PhotoIdentifier=11152.JPG, IsAlias=0, LastName=LEVINE}, {CaseCount=1, MiddleName=MARK, SexOffenderIdNumber=179737, DOB=19760619, SexOffenderDatabaseId=FL, Suffix=, Image=http://secure.hallpassid.com/SORPhotos/FL/89346.JPG, FirstName=STEVEN, PhotoIdentifier=89346.JPG, IsAlias=0, LastName=LEVINE}, {CaseCount=2, MiddleName=, SexOffenderIdNumber=429505, DOB=19760619, SexOffenderDatabaseId=NY, Suffix=, Image=http://secure.hallpassid.com/SORPhotos/NY/11152.JPG, FirstName=STEVEN, PhotoIdentifier=11152.JPG, IsAlias=1, LastName=LEVINE}]}";
	    Pattern pattern = Pattern.compile("\\{[.[^\\{}]]+\\}");
	    Matcher matcher = pattern.matcher(data);
	    ArrayList<String> matches = new ArrayList<String>();
	    while(matcher.find()){
	         matches.add(data.substring(matcher.start() + 1, matcher.end() - 1));       // +1-1 to cut off {}
	         System.out.println(data.substring(matcher.start() + 1, matcher.end() - 1));
	    }
	    for(String line : matches){
	        for(String fragment : line.split(",")){
	            System.out.println(fragment.trim());
		        
	            String[] entry = fragment.split("=");                   //split the pairs to get key and value 
	            //map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap
	        }
	    }
	}
	
	@Test
	public void FetchSORIndividualDetailsBySpringWs() {
		//GenericWebServiceImpl g = new GenericWebServiceImpl();
		try {
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("SexOffenderIdNumber", "175300");
			payLoad.put("FetchSORIndividualDetails", data);
	        
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent("YmCaSV");
	        password.setTextContent("yMCa6445");
	      //commented to resolve compilation issues - Start
	        /*Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/FetchSORIndividualDetails",usernameToken);
	        System.out.println(out);
	       
	        List<Map> sexOffenderDetails = (List<Map>) out.get("FetchSORIndividualDetailsResult");
	        
	        for(Map e: sexOffenderDetails){
	        	System.out.println(e.get("HairColor"));
	        	System.out.println(e.get("EyeColor"));
	        	System.out.println(e.get("Sex"));
	        	System.out.println(e.get("Height"));
	        	System.out.println(e.get("Weight"));
	        	System.out.println(e.get("DOB"));
	        	System.out.println(e.get("Race"));
	        	System.out.println(e.get("ScarMarksCollection"));
	        	//Object scarMarksCollection = e.get("ScarMarksCollection");
	        	//List<Map> scarMarksCollectionMap = (List<Map>) e.get("ScarMarksCollection");
	        	if(e.containsKey("ScarMarksCollection")){
		        	for(Map s: (List<Map>) e.get("ScarMarksCollection")){
		        		System.out.println(s.get("SORScarsMarksTattoosData"));
		        		
		        		for(Map d: (List<Map>) s.get("SORScarsMarksTattoosData")){
		        			System.out.println(d);
		        			System.out.println(d.get("Description"));
		        		}
		        	}
	        	}
	        }*/
	      //commented to resolve compilation issues-end
	    	/*
	    	Pattern pattern = Pattern.compile("\\{[.[^\\{}]]+\\}");
			Matcher matcher = pattern.matcher(sexOffenderDetails);
			ArrayList<String> matches = new ArrayList<String>();
			while(matcher.find()){
			     matches.add(sexOffenderDetails.substring(matcher.start() + 1, matcher.end() - 1));       // +1-1 to cut off {}
			}
			
			for(String line : matches){
			    for(String fragment : line.split(",")){
			    	 System.out.println(fragment.trim());
			    }
			} */
	        
	        //String value = StringUtils.substringBetween(sexOffenderDetails, "{", "}");
	        //System.out.println(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
