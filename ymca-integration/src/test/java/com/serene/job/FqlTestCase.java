package com.serene.job;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.serene.ws.binding.AbstractSoapParser;
import com.serene.ws.service.FusionWebService;

//@ContextConfiguration(classes = WebServiceBootStrap.class, loader = SpringApplicationContextLoader.class)
@SpringApplicationConfiguration(classes = JobBootLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class FqlTestCase {

	@Resource
	private FusionWebService fusionWebService;  
	private static Logger log = LoggerFactory.getLogger(AbstractSoapParser.class);

	
	@Test
	public void fqlWithNoWhereClause() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select OptyId,Name,OpportunityContact,PortalId_c from Opportunity  where OpportunityContact.RoleCd = 'DOES_NOT_MATTER' limit 4");   
		//log.info(response.toString());
		log.debug("<invoke");
	}	

	public void fqlWithOneFilter() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select * from Appointment where SourceObjectCd = SalesParty");   
		log.info(response.toString());
		log.debug("<invoke");
	}	
	
	public void fqlWithGroupOrCond() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select Name,PrimaryOrganizationId from Opportunity where PrimaryOrganizationId = 300000001297085 or (Name = Test)");   
		log.info(response.toString());
		log.debug("<invoke");
	}
	
	@Test
	public void fqlWithSimpleNestedQuery() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select PersonFirstName from Person where PartyType = Person and Relationship.ObjectId = 123456789");   
		log.info(response.toString());
		log.debug("<invoke");
	}
	
	public void fqlWithChildFilter() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select PersonFirstName,Relationship.ObjectId from Person where Relationship.ObjectId = 123456789");   
		log.info(response.toString());
		log.debug("<invoke");
	}
	
	@Test
	public void fqlWithChildColumns() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select PersonFirstName,Relationship.ObjectId,Email.EmailAddress from Person");   
		log.info(response.toString());
		log.debug("<invoke");
	}
	
	
	@Test
	public void fqlWithContact() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select PartyId,AccountPartyId,FirstName,LastName,DateOfBirth,SourceSystemReferenceValue,EmailAddress,LastUpdateDate,PrimaryAddress.AddressLine1,PrimaryAddress.City,PrimaryAddress.State,PrimaryAddress.PostalCode,PrimaryAddress.Country from Contact where PartyId = 300000003531886");   
		log.info(response.toString());
		System.out.println(" Phone --- >> "+response.toString());
		log.debug("<invoke");
	}
	

	@Test
	public void fqlWithColumns() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("Select Id from Location_c");   
		log.info(response.toString());
		log.debug("<invoke");
	}
	
	@Test
	public void fqlWithItemDetails() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select Id,RecordName from ProductDetails_c where RecordName = '300000003594002'");   
		log.info(response.toString());
	
		log.debug("<invoke");
	}
	
	@Test
	public void fqlWithItem() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select * from Item");   
		log.info(response.toString());
		log.debug("<invoke");
	}
	
	@Test
	public void fqlWithCustomer() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select * from Account where Type = 'ZCA_CUSTOMER' and SalesProfileStatus = 'ACTIVE' and OrganizationName = 'Govind Patwa'");   
		log.info(response.toString());
		System.out.println(" Phone --- >> "+response.toString());
		log.debug("<invoke");
	}
	
	@Test
	public void fqlLocation() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select *  from Location_c where RecordName = 'Central YMCA'");   
		log.info(response.toString());
		System.out.println(" Phone --- >> "+response.toString());
		log.debug("<invoke");
	}
	

	@Test
	public void fqlWithAccountBySourceSystem() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select * from Account where SourceSystemReferenceValue = 8 and SourceSystem = 'PORTAL'");   
		log.info(response.toString());
		System.out.println(" Phone --- >> "+response.toString());
		log.debug("<invoke");
	}
	
	@Test
	public void fqlWithContactBySourceSystem() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select * from Contact where SourceSystemReferenceValue = 8 and SourceSystem = 'PORTAL'");   
		log.info(response.toString());
		System.out.println(" Phone --- >> "+response.toString());
		log.debug("<invoke");
	}
	
	@Test
	public void fqlPaymentMethod() throws Exception{
		log.debug(">invoke");
		Map<String,Object> response = fusionWebService.query("select * from PaymentMethod_c order by LastUpdateDate ASC");   
		log.info(response.toString());
	
		log.debug("<invoke");
	}
}