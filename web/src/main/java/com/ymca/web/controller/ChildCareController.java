package com.ymca.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.WaiversAndTCDao;
import com.ymca.model.Account;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailDays;
import com.ymca.model.Location;
import com.ymca.model.PaymentMethod;
import com.ymca.model.User;
import com.ymca.model.WaiversAndTC;
import com.ymca.web.util.Constants;

@Controller
// @RequestMapping("/childcare")
@ComponentScan("com.ymca.service")
public class ChildCareController extends BaseController {
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private LocationDao locationDao;
	
	/*@Resource
	private PricingRuleDao pricingRuleDao;*/
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Resource
	private SystemPropertyDao systemPropertyDao;
	
	@Resource
	private WaiversAndTCDao waiversAndTCDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@PersistenceContext
	public EntityManager em; 
	
	/*
	@Autowired(required = true)
	private ChildCareService childCareService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute("ChildCare") ChildCare childCare) {
		return "childcare";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	protected ModelAndView createChildCareAccount(
			@ModelAttribute("ChildCare") ChildCare childCare) throws Exception {

		if (childCare.getAccountId() != null) {
			try {
				childCareService.create(childCare);
			} catch (Exception e) {

			}
		}
		String message = "New childCare " + childCare.getName()
				+ " was successfully created.";
		ModelAndView mav = new ModelAndView("final", "message", message);
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView childCareListPage(
			@ModelAttribute("ChildCare") ChildCare childCare) {
		List<ChildCare> childcareList = childCareService.findAll();
		ModelAndView mav = new ModelAndView("read", "childcareList",
				childcareList);
		return mav;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView findById(@RequestParam("accountId") Long accountId) {
		ChildCare shop = childCareService.findById(accountId);
		ModelAndView mav = new ModelAndView("find", "shop", shop);
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ModelAndView deleteChildCareEntry(@RequestParam Long accountId) {
		ChildCare childCare = childCareService.delete(accountId);
		String message = "The shop " + childCare.getName()
				+ " was successfully deleted.";
		ModelAndView mav = new ModelAndView("delete", "message", message);
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ModelAndView editShop(@ModelAttribute ChildCare childCare) {

		String message = "Shop was successfully updated.";
		childCareService.update(childCare);
		ModelAndView mav = new ModelAndView("update", "message", message);
		return mav;
	}*/
	
	@RequestMapping(value="/childcare", method=RequestMethod.GET)
    public ModelAndView childcare(Model model, final HttpServletRequest request, final HttpServletResponse response) { 
		//Model model = new ExtendedModelMap();
		
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId =authenticate.getName();
		}catch(Exception e){
			log.error("Error  ",e);
		}
		
		model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
		
		List<WaiversAndTC> terms = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
		if(terms != null && !terms.isEmpty()){
        	model.addAttribute("terms", terms.get(0).getTcDescription());
        }
		
		Account account = null;
    	User user =  null;	
    	List<PaymentMethod> paymentMethodList = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
			if(account != null){
		    	request.setAttribute("userId", userId);				
				user = getUserByAccount(account, user);
				paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
				model.addAttribute("paymentInfoLst" , paymentMethodList);
				model.addAttribute("accInfo" , account);
			}
    	}
    	model.addAttribute("gotocontact", false);
    	
        return new ModelAndView("viewChildcare", model.asMap());
    }
	
	@RequestMapping(value="/addChildcareToCart", method=RequestMethod.GET)
    public ModelAndView addChildcareToCart(final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap();
		
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId =authenticate.getName();
		}catch(Exception e){
			log.error("Error  ",e);
		}
		
		model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
		
		List<WaiversAndTC> terms = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
		if(terms != null && !terms.isEmpty()){
        	model.addAttribute("terms", terms.get(0).getTcDescription());
        }
		
		Account account = null;
    	User user =  null;	
    	List<PaymentMethod> paymentMethodList = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
			if(account != null){
		    	request.setAttribute("userId", userId);				
				user = getUserByAccount(account, user);
				paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
				model.addAttribute("paymentInfoLst" , paymentMethodList);
				model.addAttribute("accInfo" , account);
			}
    	}
    	model.addAttribute("gotocontact", true);
    	
        return new ModelAndView("viewChildcare", model.asMap());
    }
	
	@RequestMapping(value="/searchchildcare", method=RequestMethod.GET)
    public ModelAndView searchchildcare(@RequestParam String location, @RequestParam String keyword,  @RequestParam String minAgeStr,  @RequestParam String maxAgeStr, final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap(); 
		Integer minAge, maxAge;
		
		//Location loc = new Location();
		Long locId = Long.parseLong(location);
		//loc.setId(locId);
		Location loc= locationDao.findOne(locId);
		
		try {
			if(minAgeStr != null && !"".equals(minAgeStr))
	        	minAge = Integer.parseInt(minAgeStr);
	        else
	        	minAge = null;
	        
	        if(maxAgeStr != null && !"".equals(maxAgeStr))
	        	maxAge = Integer.parseInt(maxAgeStr);
	        else
	        	maxAge = null;
			
			List<ItemDetail> itemDetails = getListItemsObj(Constants.PRODUCT_CATEGORY_CHILD_CARE, loc, keyword, minAge, maxAge);
			model.addAttribute("itemDetails", itemDetails);
			
			List<Object[]> productSubtypesObj = itemDetailsDao.getDistinctCategoryByType(Constants.PRODUCT_CATEGORY_CHILD_CARE);
			productSubtypesObj.remove(StringUtils.upperCase(Constants.PRODUCT_CATEGORY_AFTER_SCHOOL));
			productSubtypesObj.remove(StringUtils.upperCase(Constants.PRODUCT_CATEGORY_INSERVICE));
			model.addAttribute("productsubtypes", productSubtypesObj);
			
			// create unique location and productsubtype lists
			Set<String> lstProductSubtypeLocationsMap = new HashSet<String>();
			Set<String> lstAllProductSubtypeLocationsMap = new HashSet<String>();
			Set<String> lstLocations = new HashSet<String>();
			
			List<Object[]> childcareObj = new ArrayList<Object[]>();
			List<String> gridfields = Arrays.asList("Type","Location","Start Time","End Time","Start Date","End Date","Age Range","Gender","M / # of available slots","Tu / # of available slots","W / # of available slots","Th / # of available slots","F / # of available slots");
			
			for(ItemDetail itemDetail : itemDetails){
				if(StringUtils.isNotBlank(itemDetail.getCategory()) && itemDetail.getCategory().equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CHILD_CARE)){
					
					Object[] ccObj = new Object[2];
					
					Object[] iObj = new Object[10]; 
					iObj[0] = gridfields.size()+1;
					iObj[1] = itemDetail.getLocation().getTier();
					iObj[2] = itemDetail.getNoofdaysinschoolyear();
					iObj[3] = itemDetail.getNoofmonthsinschoolyear();
					iObj[4] = itemDetail.getLocation().getId();
					ccObj[0] = iObj;
					
					// populate data for child care grid
					Object[] obj = new Object[gridfields.size()+2];
					obj[0] = itemDetail.getId();
					obj[1] = itemDetail.getCategory();
					obj[2] = itemDetail.getLocation().getRecordName();
					obj[3] = itemDetail.getStartTime();
					obj[4] = itemDetail.getEndTime();
					obj[5] = itemDetail.getStartDate();
					obj[6] = itemDetail.getEndDate();
					obj[7] = itemDetail.getMinAge() + " - " + itemDetail.getMaxAge();
					obj[8] = itemDetail.getGender();
					
					if(itemDetail.getWebCapacity() != null && itemDetail.getWebCapacity() <= 0 && StringUtils.isNoneBlank(itemDetail.getAutomatedWaitlist()) 
							&& itemDetail.getAutomatedWaitlist().equalsIgnoreCase(Constants.AutomatedWaitlist_NoWaitlist)){
						obj[9] = "disabled";
					}else{
						obj[9] = "";
					}
					
					List<String> days = Arrays.asList("1","2","3","4","5");
					int objCount = 9;
					for(String day: days){
						objCount = objCount+1;
						obj[objCount] = "-9";
						for(ItemDetailDays itemDays: itemDetail.getItemDays()){
							  if(itemDays.getDay()!=null && itemDays.getDay().toString().equals(day)){
								  //obj[objCount] = itemDays.getCapacity();
								  if(StringUtils.isBlank(getAgentUidFromSession()) && itemDays.getStopConfirmedSignUps() != null && itemDays.getStopConfirmedSignUps().equalsIgnoreCase("Yes")){
									  obj[objCount] = "0";
								  }else{
									  obj[objCount] = itemDays.getRemainingCapacity();
								  }
							  } 
			    		}
					}
					ccObj[1] = obj;
					
					childcareObj.add(ccObj);
					
					lstLocations.add(itemDetail.getLocation().getId().toString()+"_"+itemDetail.getLocation().getRecordName());
					lstProductSubtypeLocationsMap.add(itemDetail.getLocation().getId().toString()+"_"+itemDetail.getCategory().toString());
				}
			}
			
			for(String locs: lstLocations){
				List<String> strLocations = Arrays.asList(locs.split("_"));
				String strLocId = strLocations.get(0);
				String strLocName = strLocations.get(1);
				
				for(Object pst : productSubtypesObj){
					
					String locPstMap = strLocId+"_"+pst;
					lstAllProductSubtypeLocationsMap.add(locPstMap);
					
					boolean found = false;
					for(String productSubtypeLocationsMap: lstProductSubtypeLocationsMap){
						if(productSubtypeLocationsMap.trim().equalsIgnoreCase(locPstMap)){
							found = true;
							break;
						}
					}
					
					if(!found){
						Object[] ccObj1 = new Object[2];
						
						Object[] iObj1 = new Object[10]; 
						iObj1[0] = gridfields.size()+1;
						iObj1[1] = "";
						iObj1[2] = "";
						iObj1[3] = "";
						iObj1[4] = strLocId;
						ccObj1[0] = iObj1;
						
						// populate data for child care grid
						Object[] obj1 = new Object[gridfields.size()+1];
						obj1[0] = "-9";
						obj1[1] = pst;
						obj1[2] = strLocName;
						obj1[3] = "";
						obj1[4] = "";
						obj1[5] = "";
						obj1[6] = "";
						obj1[7] = "";
						obj1[8] = "";
						//obj1[9] = "";

						List<String> days = Arrays.asList("1","2","3","4","5");
						int objCount = 8;
						for(String day: days){
							objCount = objCount+1;
							obj1[objCount] = "-9";
							
						}
						ccObj1[1] = obj1;
						
						childcareObj.add(ccObj1);
					}
				}
			}
			
			model.addAttribute("gridfields", gridfields);
			model.addAttribute("childcareObj", childcareObj);
			
			/*
			JSONArray json = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", "1");
			jsonObj.put("productId", "11");
			json.add(jsonObj);
			model.addAttribute("childcareJsonObj", json);
			*/
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("Error  ",e1);
			return null;
		}
		
		return new ModelAndView("searchchildcare", model.asMap());
	}
	
	@RequestMapping(value="/searchInservice", method=RequestMethod.GET)
    public ModelAndView searchInservice(@RequestParam String location, @RequestParam String keyword,  @RequestParam String minAgeStr,  @RequestParam String maxAgeStr, final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap(); 
		Integer minAge, maxAge;
		
		//Location locations = new Location();
		Long locId = Long.parseLong(location);
		//loc.setId(locId);
		Location loc= locationDao.findOne(locId);
		
		try {
			if(minAgeStr != null && !"".equals(minAgeStr))
	        	minAge = Integer.parseInt(minAgeStr);
	        else
	        	minAge = null;
	        
	        if(maxAgeStr != null && !"".equals(maxAgeStr))
	        	maxAge = Integer.parseInt(maxAgeStr);
	        else
	        	maxAge = null;
			
			List<ItemDetail> itemDetails = getListItemsObj(Constants.PRODUCT_CATEGORY_INSERVICE, loc, keyword, minAge, maxAge);
			model.addAttribute("itemDetails", itemDetails);
			
			List<Object[]> inserviceObj = new ArrayList<Object[]>();
			List<String> gridfields = Arrays.asList("Name","Start Time","End Time","Start Date","End Date","Age Range");
			
			for(ItemDetail itemDetail : itemDetails){
				Object[] obj = new Object[gridfields.size()+2];
				
				obj[0] = itemDetail.getId();
				obj[1] = itemDetail.getRecordName();
				//obj[2] = itemDetail.getLocation().getRecordName();
				obj[2] = itemDetail.getStartTime();
				obj[3] = itemDetail.getEndTime();
				obj[4] = itemDetail.getStartDate();
				obj[5] = itemDetail.getEndDate();
				obj[6] = itemDetail.getMinAge() + " - " + itemDetail.getMaxAge();
				if(itemDetail.getWebCapacity() != null && itemDetail.getWebCapacity() <= 0 && StringUtils.isNoneBlank(itemDetail.getAutomatedWaitlist()) 
						&& itemDetail.getAutomatedWaitlist().equalsIgnoreCase(Constants.AutomatedWaitlist_NoWaitlist)){
					obj[7] = "disabled";
				}else{
					obj[7] = "";
				}
				//obj[6] = itemDetail.getWebCapacity();
				/*
				boolean disablePackage = true;
				List<ItemDetailDays> itemDays = itemDetail.getItemDays();
				if(itemDays.size()>0){
					for(ItemDetailDays idys : itemDays){
						disablePackage = false;
						if(idys.getRemainingCapacity().equals(0L)){
							disablePackage = true;
							break;
						}
					}
				}
				obj[6] = disablePackage;*/
				
				inserviceObj.add(obj);
			}
			
			model.addAttribute("gridfields", gridfields);
			model.addAttribute("inserviceObj", inserviceObj);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("Error  ",e1);
			return null;
		}
		
		return new ModelAndView("searchinservice", model.asMap());
	}
	
	@RequestMapping(value="/searchAfterschool", method=RequestMethod.GET)
    public ModelAndView searchAfterschool(@RequestParam String location, @RequestParam String keyword,  @RequestParam String minAgeStr,  @RequestParam String maxAgeStr, final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap(); 
		
		Integer minAge, maxAge;
		
		//Location locations = new Location();
		Long locId = Long.parseLong(location);
		//loc.setId(locId);
		Location loc= locationDao.findOne(locId);
		
		try {
			if(minAgeStr != null && !"".equals(minAgeStr))
	        	minAge = Integer.parseInt(minAgeStr);
	        else
	        	minAge = null;
	        
	        if(maxAgeStr != null && !"".equals(maxAgeStr))
	        	maxAge = Integer.parseInt(maxAgeStr);
	        else
	        	maxAge = null;
			
			List<ItemDetail> itemDetails = getListItemsObj(Constants.PRODUCT_CATEGORY_AFTER_SCHOOL, loc, keyword, minAge, maxAge);
			model.addAttribute("itemDetails", itemDetails);
			
			List<Object[]> afterschoolObj = new ArrayList<Object[]>();
			List<String> gridfields = Arrays.asList("Name","Start Time","End Time","Start Date","End Date","Age Range","Remaining Capacity");
			
			for(ItemDetail itemDetail : itemDetails){
				Object[] obj = new Object[gridfields.size()+2];
				
				obj[0] = itemDetail.getId();
				obj[1] = itemDetail.getRecordName();
				//obj[2] = itemDetail.getLocation().getRecordName();
				obj[2] = itemDetail.getStartTime();
				obj[3] = itemDetail.getEndTime();
				obj[4] = itemDetail.getMinAge() + " - " + itemDetail.getMaxAge();
				obj[5] = itemDetail.getRemainingCapacity();
				//obj[6] = itemDetail.getRegistrationPrice();
				obj[6] = itemDetail.getStartDate();
				obj[7] = itemDetail.getEndDate();
				if(itemDetail.getWebCapacity() != null && itemDetail.getWebCapacity() <= 0 && StringUtils.isNoneBlank(itemDetail.getAutomatedWaitlist()) 
						&& itemDetail.getAutomatedWaitlist().equalsIgnoreCase(Constants.AutomatedWaitlist_NoWaitlist)){
					obj[8] = "disabled";
				}else{
					obj[8] = "";
				}
						
				afterschoolObj.add(obj);
			}
			
			model.addAttribute("gridfields", gridfields);
			model.addAttribute("afterschoolObj", afterschoolObj);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log.error("Error  ",e1);
			return null;
		}
		
		return new ModelAndView("searchafterschool", model.asMap());
	}
	private List<ItemDetail> getListItemsObj(String programSubtype, Location location, String keyword, Integer minAge, Integer maxAge) {
		
		List<Location> bayareaLocations = new ArrayList<Location>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ItemDetail> query = builder.createQuery(ItemDetail.class);
		Root<ItemDetail> itemDetail = query.from(ItemDetail.class);
		Join<ItemDetail, Location> l = itemDetail.join("location",JoinType.INNER);
		
		ParameterExpression<String> prodType = builder.parameter(String.class);
		ParameterExpression<String> prodSubtype = builder.parameter(String.class);
		ParameterExpression<String> kword = builder.parameter(String.class);
		//ParameterExpression<Integer> minAge = builder.parameter(Integer.class);
		//ParameterExpression<Integer> maxAge = builder.parameter(Integer.class);
		ParameterExpression<String> prodDescription = builder.parameter(String.class);
		ParameterExpression<Location> loc = builder.parameter(Location.class);
		ParameterExpression<Integer> minAgeParam = builder.parameter(Integer.class);
		ParameterExpression<Integer> maxAgeParam = builder.parameter(Integer.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(
				builder.and(
						builder.equal(itemDetail.get("type"), prodType)
				)
		);
		
		if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(programSubtype.trim())){
			predicates.add(builder.and(builder.notEqual(itemDetail.<String>get("category"), Constants.PRODUCT_CATEGORY_AFTER_SCHOOL)));
			predicates.add(builder.and(builder.notEqual(itemDetail.<String>get("category"), Constants.PRODUCT_CATEGORY_INSERVICE)));
		} else {
			predicates.add(builder.and(builder.equal(itemDetail.<String>get("category"), prodSubtype)));
		}
		
		if(minAge != null && !"".equals(minAge) && maxAge != null && !"".equals(maxAge)){
			predicates.add(
				builder.or(
					builder.or(
						builder.between(minAgeParam, itemDetail.<Integer>get("minAge"), itemDetail.<Integer>get("maxAge")),
						builder.between(maxAgeParam, itemDetail.<Integer>get("minAge"), itemDetail.<Integer>get("maxAge"))
					),
					
					builder.and(
						builder.between(itemDetail.<Integer>get("minAge"), minAgeParam, maxAgeParam),
						builder.between(itemDetail.<Integer>get("maxAge"), minAgeParam, maxAgeParam)
					)
				)
			);
		}
		
		if(!"".equals(keyword.trim())){
			predicates.add(builder.and(builder.like(itemDetail.<String>get("recordName"), kword)));
			predicates.add(builder.or(
					builder.like(itemDetail.<String>get("recordName"), kword),
					builder.like(itemDetail.<String>get("description"), prodDescription)
			));
		}
		
		String allBranchLocation = Constants.LOCATION_ALL_BRANCH;
		String bayAreaLocation = Constants.LOCATION_BAYAREA;
		if(systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_ALL_BRANCH).size()>0){
			allBranchLocation = systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_ALL_BRANCH).get(0).getKeyValue().toString();
		}
		if(systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_BAYAREA).size()>0){
			bayAreaLocation = systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_BAYAREA).get(0).getKeyValue().toString();
		}
		if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(allBranchLocation)){}
		else if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(bayAreaLocation)){
			 bayareaLocations = locationDao.getLocationsByArea(Constants.LOCATION_BAYAREA);
			 Collection<Location> lstlocations = bayareaLocations;
			 //Expression<Collection<Locations>> lstLocations = lstlocations;
			 Expression<Location> lstloc = itemDetail.get("location");
			 predicates.add(lstloc.in(lstlocations));
		}
		else {
			predicates.add(builder.and(builder.equal(itemDetail.<Location>get("location"), loc)));
		}
		query.select(itemDetail);
		query.orderBy(builder.desc(itemDetail.get("category")));
		query.where(predicates.toArray(new Predicate[]{}));

		TypedQuery<ItemDetail> typequery = em.createQuery(query);
		typequery.setParameter(prodType, Constants.PRODUCT_CATEGORY_CHILD_CARE);
		if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(programSubtype.trim())){
			
		} else {
			typequery.setParameter(prodSubtype, programSubtype);
		}
		
		if(minAge != null && !"".equals(minAge) && maxAge != null && !"".equals(maxAge)){
			typequery.setParameter(minAgeParam, minAge);
			typequery.setParameter(maxAgeParam, maxAge);
		}
		
		if(!"".equals(keyword.trim())){
			typequery.setParameter(kword, "%"+keyword.trim()+"%");
			typequery.setParameter(prodDescription, "%"+keyword.trim()+"%");
		}
		if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(allBranchLocation)){}
		else if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(bayAreaLocation)){
		}
		else {
			typequery.setParameter(loc, location);
		}
		
		List<ItemDetail> lstObj = typequery.getResultList();
		
		return lstObj;
	}
	
	
}
