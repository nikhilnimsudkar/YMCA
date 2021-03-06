package com.ymca.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.serene.ws.service.impl.GenericWebServiceImpl;
import com.ymca.dao.NotesDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Notes;
import com.ymca.model.User;

@Service
@ComponentScan("com.ymca")
public class HallPassServiceImpl implements HallPassService {
	
	private static final Log log = LogFactory.getLog(HallPassServiceImpl.class);
	
	@Resource(name="genericWebServiceImpl")
	GenericWebServiceImpl genericWebService;
	
	@Autowired(required=true)
	private NotesDao notesDao;
	
	@Autowired(required=true)
	private UserDao userDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public boolean isSexOffender(Map<String, Object> userData) {
		try {
			
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("FirstName", userData.get("firstName").toString());
		 	data.put("LastName", userData.get("lastName").toString());
		 	data.put("DOB", sdf.format(userData.get("dateOfBirth")));
			payLoad.put("IsSOR", data);
		    
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent("YmCaSV");
	        password.setTextContent("yMCa6445");
		    
		    //Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/IsSOR",usernameToken);
		    //System.out.println(out);
		    //return Boolean.parseBoolean(out.get("IsSORResult").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Object FetchSORDetails(Map<String, Object> userData) {
		try {
		 	Map<String, Object> payLoad = new HashMap<String,Object>();
		 	Map<String,String> data = new HashMap<String,String>();
		 	data.put("FirstName", userData.get("firstName").toString());
		 	data.put("LastName", userData.get("lastName").toString());
		 	data.put("DOB", sdf.format(userData.get("dateOfBirth")));
		 	data.put("PageCount", "1");
		 	data.put("PageSize", "10");
			payLoad.put("FetchSORDetails", data);
	        
			QName userCred = new QName("http://HallPassId.com/SORData/", "UserCredential","sor");
			SOAPElement usernameToken = SOAPFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createElement(userCred);
			SOAPElement username = usernameToken.addChildElement("UserName","sor","http://HallPassId.com/SORData/");
	        SOAPElement password = usernameToken.addChildElement("Password","sor","http://HallPassId.com/SORData/");
	        username.setTextContent("YmCaSV");
	        password.setTextContent("yMCa6445");
	        
	        //Map out = genericWebService.invoke(payLoad,"http://secure.hallpassid.com/SORServices/SORSearch.asmx?WSDL", "http://HallPassId.com/SORData/FetchSORDetails",usernameToken);
	        //System.out.println(out);
	        
	        //return out.get("FetchSORDetailsResult");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> getOffenderIds(Map<String, Object> userData, String agentId) {
		
		List<String> offenderIds = new ArrayList<String>();
		
		List<Map> sexOffenderDetails = (List<Map>) FetchSORDetails(userData);
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
	    		    if(addSoToList)
	    		    	offenderIds.add(o.get("SexOffenderIdNumber").toString());

	           }
	      }
		
		 //return StringUtils.join(offenderIds, ',');
		return offenderIds;
	}
}
