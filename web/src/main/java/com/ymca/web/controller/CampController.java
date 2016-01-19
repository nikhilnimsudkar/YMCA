package com.ymca.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymca.dao.ItemDetailAssociatedItemDetailDao;
import com.ymca.dao.ItemDetailPricingRuleDao;
import com.ymca.model.ItemDetailAssociatedItemDetail;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.web.util.Constants;

@Controller
public class CampController extends BaseController {

	@Resource
	private ItemDetailAssociatedItemDetailDao itemDetailAssociatedItemDetailDao ; 
	
	@Resource
	private ItemDetailPricingRuleDao  itemDetailPricingRuleDao ;
	
	@RequestMapping(value="/sc/agent/getItemActivityAndTransportService",method = RequestMethod.GET)
	public @ResponseBody List<LinkedHashMap<String,Object>> getCampActivities(String itemDetailId,String cIds) throws Exception {
	
		List<Long> ids = new ArrayList<Long>();
		String[] itemDetailIds = itemDetailId.split(",");
		for (String i : itemDetailIds) {
			ids.add(Long.parseLong(i));
		}
		List<String> types = new ArrayList<String>();
		types.add("Activity");
		types.add("Transportation");
		List<ItemDetailAssociatedItemDetail> objs = itemDetailAssociatedItemDetailDao.findAssociatedItemDetailTypeByItemDetailIdInAndTypeInAndStatusOrderByActivityPriorityAsc(ids, types, Constants.Status_Active);
		List<LinkedHashMap<String, Object>> resp = new ArrayList<LinkedHashMap<String,Object>>();
		
		for (ItemDetailAssociatedItemDetail o: objs) {
			
			String[] cName = cIds.split(",");
			for (String c : cName) {
				Long contactId = 0L;
				StringBuffer contactsForItem = new StringBuffer("");
				String[] cIdName = c.split("_");
				if(cIdName.length == 3){
					if(Long.parseLong(cIdName[0]) == o.getItemDetailId()){
						contactsForItem.append(cIdName[2]+",");
						contactId = Long.parseLong(cIdName[1]);
					
						if(contactsForItem.length() > 0){
							contactsForItem.deleteCharAt(contactsForItem.length()-1);
						}
						
						LinkedHashMap<String,Object> r= new LinkedHashMap<String, Object>();
						
						// set common fields 
						r.put("id", contactsForItem.toString()  + " " +o.getId());
						r.put("groupField", contactsForItem.toString() +" "+ o.getItemDetail().getRecordName());
						r.put("cId", contactsForItem.toString());
						r.put("contactId", contactId.toString());
										
						r.put("parentItemId", o.getItemDetailId());
						r.put("associatedItemId", o.getAssociatedItemDetailId());
						
						// set activity related fields 
						r.put("type", o.getType());
						r.put("name", o.getRecordName());
						r.put("priority", o.getActivityPriority());
						//r.put("type", o.getType());
						
						// set transport related fields
						
		/*				r.put("location", "Location 1");
						r.put("serviceType", "Airport");
						r.put("direction", "DROPOFF");
						r.put("startDate", "2015-03-13");
						r.put("startTime", "11:12:11");
						r.put("endDate", "2015-03-13");
						r.put("endTime", "2015-03-13");*/
		
						
						r.put("location", o.getAssociatedItemDetail().getLocation().getRecordName());
						r.put("serviceType", o.getAssociatedItemDetail().getSubCategory());
						r.put("direction", o.getAssociatedItemDetail().getDirection());
						r.put("startDate", o.getAssociatedItemDetail().getStartDate());
						r.put("startTime", o.getAssociatedItemDetail().getStartTime());
						r.put("endDate", o.getAssociatedItemDetail().getEndDate());
						r.put("endTime", o.getAssociatedItemDetail().getEndTime());
						
						
						// TODO need to refactor this and combine into single query to fetch item details and associated pricing rules
						List<ItemDetailPricingRule> lstPricingrule = itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(o.getAssociatedItemDetail().getId(),Constants.Status_Active,Constants.Status_Active,Constants.SIGNUP);
						Integer memberPrice = 0 ;
						Integer nonMemberPrice = 0 ;
						if (lstPricingrule != null && !lstPricingrule.isEmpty()) {
							 for (ItemDetailPricingRule pr : lstPricingrule) {
								 memberPrice = memberPrice + pr.getPricingRule().getTierPrice().intValue();
								 nonMemberPrice = nonMemberPrice + pr.getPricingRule().getNonmemberTierPrice().intValue();
							 }
							
						}
						r.put("agreed", false);
						r.put("memberPrice", memberPrice);
						r.put("nonmemberprice", nonMemberPrice);
						r.put("flightName", "");
						r.put("flightTime", "");
						
						resp.add(r);
				
					}
				}
			}
			//}
		}
		
		return resp ;
	}
	
	@RequestMapping(value="/sc/agent/testActivityAndTransportService",method = RequestMethod.GET)
	public String testActivityAndTransportService(String itemDetailId) throws Exception {
		return "testActivityAndTransportServicePage" ;
	}
}