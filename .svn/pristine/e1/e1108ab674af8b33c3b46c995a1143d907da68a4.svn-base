package com.ymca.web.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ymca.dao.ItemDetailDao;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailDays;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.Location;
import com.ymca.model.PricingRule;
import com.ymca.web.model.SearchGrid;
import com.ymca.web.util.Constants;

@Service
@Scope("prototype")
public class SearchItemDetailService {

	@PersistenceContext
	public EntityManager em; 
	
	private CriteriaBuilder builder ; 
	private CriteriaQuery<SearchGrid> query ;
	private Root<ItemDetail> i ;
	private Join<ItemDetail, Location> l ;
	
	private Join<ItemDetail, ItemDetailDays> days ;
	private Join<ItemDetail, ItemDetailPricingRule> pricingRule ;
	
	private Expression dayIds ;
	private Expression instructor ;
	private Expression sessionName ;
	private Expression mp ;
	private Expression nmp ;
	
	private TypedQuery<SearchGrid> typequery ;
	
	private ParameterExpression<String> prodType ;
	private ParameterExpression<Date> sd ;
	private ParameterExpression<Date> ed ;
	private ParameterExpression<String> catP ;
	private ParameterExpression<String> recordName ;
	private ParameterExpression<Location> loc ;
	private ParameterExpression<List> idsParam ;
	
	@Resource
	private ItemDetailDao itemDetailDao;
	
	@PostConstruct
	public void init(){
		
		// build criteria
		builder = em.getCriteriaBuilder();
		query = builder.createQuery(SearchGrid.class);
		i = query.from(ItemDetail.class);
		l = i.join("location",JoinType.INNER);
		
		// Join item detail days
		days = i.join("itemDays", JoinType.LEFT);
		dayIds = builder.function("GROUP_CONCAT", String.class, days.get("day"));
		instructor = builder.function("GROUP_CONCAT", String.class, days.get("instructorName"));
		sessionName = builder.function("GROUP_CONCAT", String.class, days.get("sessionName"));
		
		// Join Item detail price and filter on pricing type sign up
		pricingRule = i.join("pricingRules", JoinType.LEFT);
		mp = builder.function("MAX", Double.class, pricingRule.get("tierPrice"));
		nmp = builder.function("MAX", Double.class, pricingRule.get("nonmemberTierPrice"));
		
		// set columns
		setColumns();
		
		// set parameters
		prodType 	= builder.parameter(String.class);
		sd 			= builder.parameter(Date.class);
		ed 			= builder.parameter(Date.class);
		catP 		= builder.parameter(String.class);
		recordName 	= builder.parameter(String.class);
		loc 		= builder.parameter(Location.class);
		idsParam	= builder.parameter(List.class);
		
	}
	
	public List<SearchGrid> search(String type,String category,String productname, String  locationId, String fDate,String tDate,String itemDetailIds, String agentUidFromSession) throws Exception {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		DateFormat inputsdf = new SimpleDateFormat("M/d/yyyy");  

		Location location = new Location();
		Long locId = Long.parseLong(locationId);
		location.setId(locId);
		
		Date fromDate;
        Date toDate;
        
		fromDate = sdf.parse(sdf.format(inputsdf.parse(fDate)));
        toDate = sdf.parse(sdf.format(inputsdf.parse(tDate)));
		
		// set filter conditions
		List<Predicate> predicates = setFilters(type,category, productname,fromDate,toDate,location,itemDetailIds);

		query.where(predicates.toArray(new Predicate[]{}));
		query.groupBy(i.get("id"));

		typequery = em.createQuery(query);
		/*
		typequery.setParameter(prodType, type);
		typequery.setParameter(sd, fromDate);
		typequery.setParameter(ed, toDate);

 		if(!"All".equalsIgnoreCase(category)){
			typequery.setParameter(catP, category.trim());
		}

 		if(!"".equals(productname.trim())){
			typequery.setParameter(recordName, productname.trim()+"%");
		}

		if(!(location.getId()==1 || location.getId()==2)){
			typequery.setParameter(loc, location);
		}*/
/*		if (StringUtils.isNoneBlank(itemDetailIds)) {
			List<Long> ids = new ArrayList<Long>();
			String[] itIds = itemDetailIds.split(",");
			for (String id : itIds) {
				ids.add(Long.parseLong(id));
			}
			typequery.setParameter(idsParam, ids);
		}*/
		
		List<SearchGrid> lstObj = typequery.getResultList();

		if(lstObj != null){
			for (SearchGrid record : lstObj) {
				
				ItemDetail itemDetail = itemDetailDao.findOne(record.getId());
				
				if(StringUtils.isBlank(agentUidFromSession) && itemDetail.getStopConfirmedSignUps() != null && itemDetail.getStopConfirmedSignUps().equalsIgnoreCase("Yes")){
					record.setCapacity(0L);
				}
				if((record.getProductType().equalsIgnoreCase(Constants.PRODUCT_CATEGORY_PROGRAM) || record.getProductType().equalsIgnoreCase(Constants.CAMP_TYPE) || record.getProductType().equalsIgnoreCase(Constants.PRODUCT_CATEGORY_EVENT)) 
						&& record.getWebCapacity() != null && record.getWebCapacity() <= 0 && StringUtils.isNoneBlank(record.getAutomatedWaitlist()) && record.getAutomatedWaitlist().equalsIgnoreCase(Constants.AutomatedWaitlist_NoWaitlist)){
					record.setCheckBoxState("disabled");
				}else{
					record.setCheckBoxState("");
				}
			}
		}
		return lstObj;
	}

	private List<Predicate> setFilters(String type,String category, String productname,Date fromDate,Date toDate,Location location,String itemDetailIds) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		List<String> cats = new ArrayList<String>();
		cats.add(Constants.CAMP_CATEGORY_ACTVITY);
		cats.add(Constants.CAMP_CATEGORY_ACTVITY);
		
		predicates.add(
			builder.and(
				//builder.not(i.get("category").in(cats)),
				builder.equal(i.get("type"), type),
				builder.greaterThanOrEqualTo(i.<Date>get("startDate"), fromDate),
				builder.lessThanOrEqualTo(i.<Date>get("startDate"), toDate),
				builder.and(builder.equal(pricingRule.<PricingRule>get("type"),Constants.PRICING_RULE_SIGN_UP))
			)
		);
		
		if(!"All".equalsIgnoreCase(category)){
			predicates.add(builder.and(builder.equal(i.<String>get("category"), category)));
		}
		
		if(!"".equals(productname.trim())){
			predicates.add(builder.and(builder.like(i.<String>get("recordName"), productname)));
		}
		
		if(location.getId()==2){
			 predicates.add(builder.and(builder.equal(l.get("area"), Constants.LOCATION_BAYAREA)));
		}
		else if(location.getId()!=1){
			predicates.add(builder.and(builder.equal(i.<Location>get("location"), location)));
		}
		
		if (StringUtils.isNoneBlank(itemDetailIds)) {
			List<Long> ids = new ArrayList<Long>();
			String[] itIds = itemDetailIds.split(",");
			for (String id : itIds) {
				ids.add(Long.parseLong(id));
			}
			Expression<Long> exp = i.get("id");
			Predicate predicate = exp.in(ids);
			predicates.add(predicate);
		}
		
		return predicates;
	}

	/**
	 * The columns names should have same constructor
	 */
	private void setColumns() {

		List<Selection<?>> selectionList =  new ArrayList<Selection<?>>();
		selectionList.add(i. get("id"));
		selectionList.add(i. get("recordName").alias("productName"));
		selectionList.add(i. get("description").alias("productDesc"));
		selectionList.add(i. get("category").alias("productCategory"));
		selectionList.add(l. get("recordName").alias("branchName"));
		selectionList.add(i. get("startDate"));
		selectionList.add(i. get("startTime"));
		selectionList.add(i. get("endDate"));
		selectionList.add(i. get("endTime"));
		selectionList.add(i. get("remainingCapacity").alias("capacity"));				
		selectionList.add(i. get("gender"));
		selectionList.add(mp.alias("memberprice"));
		selectionList.add(nmp.alias("nonmembertierPrice"));
		selectionList.add(i. get("lodgingType"));
		selectionList.add(instructor.alias("instructorName"));
		selectionList.add(sessionName.alias("sessionName"));
		selectionList.add(dayIds.alias("dayId"));
		selectionList.add(i. get("minAge"));
		selectionList.add(i. get("maxAge"));
		selectionList.add(i. get("actualRemainingCapacity").alias("actualRemainingCapacity"));
		selectionList.add(i. get("webCapacity"));
		selectionList.add(i. get("automatedWaitlist"));
		selectionList.add(i. get("type").alias("productType"));		
		query.multiselect(selectionList);
	}
}