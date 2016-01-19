package com.ymca.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import com.serene.bootstrap.WebServiceBootStrap;
import com.serene.ws.binding.CustomSoapParser;
import com.serene.ws.exception.WebServiceException;
import com.serene.ws.service.impl.GenericWebServiceImpl;
import com.sun.xml.ws.developer.WSBindingProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebServiceBootStrap.class)
//@ComponentScan(basePackages={"com.ymca"})
//@WebAppConfiguration
//@ContextConfiguration(classes = {WebConfig.class, WebAppBootLoader.class, WebSecurityConfig.class })
public class SSOIntegrationTest  {
	
	@Resource(name="genericWebServiceImpl")
	GenericWebServiceImpl genericWebService;
	
	@Resource
	private CustomSoapParser customSoapParser ;  

	@Test
	public void getSessionAuthenticated() {		
		//HttpServletRequest request = mock(HttpServletRequest.class);       
	    //HttpServletResponse response = mock(HttpServletResponse.class); 
	    String username = "iuser";
        String password = "Serene123";
        
        Map<String, Object> requestContext = new HashMap<String, Object>();
        requestContext.put(WSBindingProvider.USERNAME_PROPERTY, username);
        requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, password);
        requestContext.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IlN3UjQ5eW8xblV5UTM5SDI5akhQOHNYei0tUSJ9.eyJleHAiOjE0MzkyMjU2NzUsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiSVVTRVIiLCJpYXQiOjE0MzkyMTEyNzV9.IFitCS_Tl-zBTBg-iXInkjkLgLmmmXIYNl_cIxyttTXhVOxtgmH1J6lBO-JhfHNka0BaNDe8lZvtF5In5Mf_dRRhYIDWVtKNXz9rAM4rcMn-Jryjy9nbenXN33KTScAiY6nTrNHZt7ybFR0-jQMpj1eO6QcyGpTZyLveVcvVwzM");
        
		try {
			 String strXML = new String(""
			      +"<soapenv:Body>"
			         +" <typ:findSelfUserDetails/>"			         
			      +"</soapenv:Body>"
			   );
			
			 ClientInterceptor[] interceptors = new ClientInterceptor[1];
			 /*Wss4jSecurityInterceptor wsSecurityInterceptor = new Wss4jSecurityInterceptor();
			 wsSecurityInterceptor.setSecurementUsername("iuser");
			 wsSecurityInterceptor.setSecurementPassword("Serene123");
			 wsSecurityInterceptor.setSecurementActions("UsernameToken");
			 //wsSecurityInterceptor.setSecurementPasswordType(WSConstants.PASSWORD_TEXT);
			 interceptors[0] = wsSecurityInterceptor;
			 genericWebService.setInterceptors(interceptors);
			 Map<String, Object> responseObj = genericWebService.invoke(strXML, "https://eaag-test.hcm.us2.oraclecloud.com/hcmPeopleRolesV2/UserDetailsService?wsdl", "http://xmlns.oracle.com/apps/hcm/people/roles/userDetailsServiceV2/findSelfUserDetails");
			System.out.println(responseObj);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
