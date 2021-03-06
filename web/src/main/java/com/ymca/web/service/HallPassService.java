package com.ymca.web.service;

import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.serene.ws.service.impl.GenericWebServiceImpl;
import com.ymca.dao.AccountDao;
import com.ymca.dao.NotesDao;
import com.ymca.model.Notes;
import com.ymca.model.User;
import com.ymca.web.model.SexOffender;
import com.ymca.web.util.Constants;
import com.ymca.web.util.PropFileUtil;

@Service 
public class HallPassService {
	@Resource(name="genericWebServiceImpl")
	GenericWebServiceImpl genericWebService;
	
	@Resource
	private NotesDao notesDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean isSexOffender(User user) {
		try {
			
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("FirstName", user.getFirstName());
		 	data.put("LastName", user.getLastName());
		 	data.put("DOB", sdf.format(user.getDateOfBirth()));
			payLoad.put("IsSOR", data);
		    
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
		    SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
		    username.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_USERNAME));
		    password.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_PASSWORD));
		    
		    //Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/IsSOR",usernameToken);
		   // System.out.println(out);
		   // return Boolean.parseBoolean(out.get("IsSORResult").toString());
		    
		    return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Object FetchSORDetails(User user) {
		try {
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("FirstName", user.getFirstName());
		 	data.put("LastName", user.getLastName());
		 	data.put("DOB", sdf.format(user.getDateOfBirth()));
		 	data.put("PageCount", "1");
		 	data.put("PageSize", "10");
			payLoad.put("FetchSORDetails", data);
	        
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_USERNAME));
		    password.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_PASSWORD));
	        
	        //Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/FetchSORDetails",usernameToken);
	        //System.out.println(out);
	        
	        //return out.get("FetchSORDetailsResult");
		    return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Object FetchSORIndividualDetails(String SexOffenderIdNumber) {
		try {
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("SexOffenderIdNumber", SexOffenderIdNumber);
			payLoad.put("FetchSORIndividualDetails", data);
	        
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_USERNAME));
		    password.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_PASSWORD));
	        
	        //Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/FetchSORIndividualDetails",usernameToken);
	        //System.out.println(out);
		    //return out.get("FetchSORIndividualDetailsResult");
		    return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Object FetchSORIndividualOffenses(String SexOffenderIdNumber) {
		try {
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("SexOffenderIdNumber", SexOffenderIdNumber);
			payLoad.put("FetchSORIndividualOffenses", data);
	        
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_USERNAME));
		    password.setTextContent(PropFileUtil.getValue(Constants.HALLPASS_PASSWORD));
	        
	        //Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/FetchSORIndividualOffenses",usernameToken);
	        //System.out.println(out);
	        
	        //return out.get("FetchSORIndividualOffensesResult");
		    return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Notes> getOffenderIds(User user, String agentId) {
		List<String> offenderIds = new ArrayList<String>();
		List<Notes> notesObj = new ArrayList<Notes>();
		
		List<Map> sexOffenderDetails = (List<Map>) FetchSORDetails(user);
		for(Map e: sexOffenderDetails){
	          for(Map o: (List<Map>) e.get("SORResData")){
	        		//System.out.println(o.get("SexOffenderIdNumber"));
	        		Boolean addSoToList = true;
	    		    if(offenderIds.size()>0){
	    		    	for(String s: offenderIds){
	    		    		if(s!=null && s.equalsIgnoreCase(o.get("SexOffenderIdNumber").toString())){
	    		    			addSoToList = false;
	    		    			break;
	    		    		}
	    		    	}
	    		    }
	    		    if(addSoToList){
	    		    	offenderIds.add(o.get("SexOffenderIdNumber").toString());
	    		    	
	    		    	Boolean addNotes = true;
	    		    	if(user.getNotes()!=null && user.getNotes().size()>0){
	    					for(Notes n: user.getNotes()){
	    						if(n.getOffenderId()!=null && o.get("SexOffenderIdNumber").toString().equals(n.getOffenderId()))
	    							addNotes = false;
	    					}
	    		    	}
	    		    	
	    		    	if(addNotes){
		    		    	Notes notes = new Notes();
		    		    	notes.setContact(user);
		    		    	notes.setOffenderId(o.get("SexOffenderIdNumber").toString());
		    		    	notes.setNoteType(Constants.SEXOFFENDER);
		    		    	notes.setPortalModifiedBy(agentId);
		    		    	notesDao.save(notes);
		    		    	
		    		    	notesObj.add(notes);
	    		    	}
	    		    }
	           }
	      }
		
		 //return StringUtils.join(offenderIds, ',');
		return notesObj;
	}
}
