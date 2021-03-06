package com.ymca.web.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ymca.dao.SignupDao;
import com.ymca.model.ItemDetail;
import com.ymca.model.Signup;
import com.ymca.model.User;

@Service
@Transactional
public class PromoExpressionEvaluator {
	
	private Logger log = LoggerFactory.getLogger(PromoExpressionEvaluator.class);
	
	@PersistenceContext
	public EntityManager entityManager;
	
	@Resource
	public SignupDao signupDao;
	
	public boolean expressionResult = false;
	
	@Resource
	private PlatformTransactionManager transactionManager;

	
	@SuppressWarnings({ "rawtypes" })
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void evaluatePromotionExpressionTransactional(String sqlExpression, Signup signup, List<Signup> cartSignups) {
		
		//Long counter = null; //signupDao.count();
		
		if(cartSignups != null && !cartSignups.isEmpty()){
			DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
	/*		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);*/
			//definition.setReadOnly(true);
			TransactionStatus transaction = transactionManager.getTransaction(definition);
			transaction.setRollbackOnly();
			
			/*counter = signupDao.count();
			log.info("  counter1  "+counter);*/
			
			//log.info("  cartSignups.size()  >>>>>>  "+cartSignups.size());
			
				for (Signup st : cartSignups) {
					//Signup signup1 = 
					signupDao.save(st);
					//log.info("  Signup1  "+signup1.getSignupId());
				}
		}
		//counter = signupDao.count();
		//log.info("  counter2  "+counter);
		if(sqlExpression != null && !sqlExpression.equals("")){
			Set<String> keywords = getExpressionKeywords(sqlExpression);
			if(keywords != null && keywords.size() > 0){
				
				ItemDetail itemDetail = signup.getItemDetail();
				User contact = signup.getContact();
				//Account account = contact.getCustomer();
				
				//Map<String, Object> values = getPromotionExpressionValues(signup);
				for (String keyword : keywords) {
					
					log.info("  keyword  :: "+keyword);
					String[] str = keyword.split("\\.");
				
					if(str.length == 2){
						
						log.info("  str  :: "+str);
						String table = str[0];
						String column = str[1];
						log.info("  table  ::  "+table+"   column  ::  "+column);
						
						Object value = null;
						
						switch (table) {
						case "ItemDetail":
							value = getFieldValue(itemDetail, column);
							
							break;

						case "Signup":
							value = getFieldValue(signup, column);
							
							break;
							
						case "User":
							value = getFieldValue(contact, column);
							
							break;
						default:
							break;
						}
						
						if(value != null && !value.toString().equals("")){
							sqlExpression = sqlExpression.replaceAll("<"+keyword+">", value.toString());
						}
						
					}else if(str.length == 3){
						
						log.info("  str  :: "+str);
						String table1 = str[0];
						String table2 = str[1];
						String column = str[2];
						log.info("  table1   ::  "+table1+"   table2   ::  "+table2+"   column  ::  "+column);
						
						Object table2Obj = null, value = null;
						
						switch (table1) {
						case "ItemDetail":
							table2Obj = getFieldValue(itemDetail, table2);
							
							break;

						case "Signup":
							table2Obj = getFieldValue(signup, table2);
							
							break;
							
						case "User":
							table2Obj = getFieldValue(contact, table2);
							
						default:
							break;
						}
						
						//if(table2Obj != null){
							//log.info("  table2Obj.getClass()  ::  "+table2Obj.getClass());
							
							//Account ac = (Account)table2Obj;
							
							//log.info("  AccountId  :: "+ac.getAccountId());
							
						//}
						
						value = getFieldValue(table2Obj, column);
						
						if(value != null && !value.toString().equals("")){
							sqlExpression = sqlExpression.replaceAll("<"+keyword+">", value.toString());
						}
					}
					
					/*Object value = null;
					switch (keyword) {
					
					case Constants.PromotionExpressionKeyword_Signup_ContactId:
					case Constants.PromotionExpressionKeyword_Signup_ItemDetailId:
						
						value = values.get(keyword);
						if(value != null && !value.toString().equals("")){
							sqlExpression = sqlExpression.replaceAll("<"+keyword+">", value.toString());
						}
						break;
							
					default:
						break;
					}*/
				}
			}
		}
		log.info("  sqlExpression  ::  "+sqlExpression);
		
		//counter = signupDao.count();
		//log.info("  counter3  "+counter);
		
		if(sqlExpression != null && !sqlExpression.equals("")){
			Set<String> keywords = getExpressionKeywords(sqlExpression.toString());
			if(keywords != null && keywords.size() > 0){
				// do nothing
				log.error("Unable to evaluate promotion expression - "+sqlExpression);
			}else{
				
				/*List resultList1 = entityManager.createNativeQuery("select count(*) from Signup where accountId = 279 and item_detail_id = 300000003665156 and status = 'Confirmed' ").getResultList();
				log.info("  resultList1  =>  "+resultList1.get(0));*/
				
				// execute query
				List resultList = entityManager.createNativeQuery(sqlExpression.toString()).getResultList();
				if(resultList != null && resultList.size() > 0){
					expressionResult = true;
					
					this.setExpressionResult(true);
					
					log.info("  Result found :: "+resultList.size());
				}else{
					// do nothing
					expressionResult = false;
					this.setExpressionResult(false);
					log.info("  Result not found");
				}
			}
		}
		/*counter = signupDao.count();
		log.info("  counter4  "+counter);*/
		if(cartSignups != null && !cartSignups.isEmpty()){
			throw new RuntimeException();
		}
	}
	
	public static final Pattern TAG_REGEX = Pattern.compile("<(.+?)>");

	public static Set<String> getExpressionKeywords(final String str) {
	    final Set<String> tagValues = new HashSet<String>();
	    final Matcher matcher = TAG_REGEX.matcher(str);
	    while (matcher.find()) {
	        tagValues.add(matcher.group(1));
	    }
	    return tagValues;
	}
	
	@SuppressWarnings("rawtypes")
	public Object getFieldValue(Object obj, String fieldName){
		Object value = null;
		Class c = null;
		try {
			c = obj.getClass();
			//Field field = c.getDeclaredField(fieldName);
			//value = field.get(obj);
			
			value = MethodUtils.invokeMethod(obj, prepareGetMethod(fieldName), null);
			
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | NullPointerException e) {
			if(c != null){
				log.error("Unable to evaluate promotion expression for the class.field - "+c.getName()+"."+fieldName);
			}else{
				log.error("Unable to evaluate promotion expression for the field - "+fieldName);
			}
			log.error("Exception : "+e.getMessage());
		}
		return value;
	}
	
	public String prepareGetMethod(String fieldName){
		
		StringBuffer rs = new StringBuffer();
		
		char first = Character.toUpperCase(fieldName.charAt(0));
		rs.append("get" + first + fieldName.substring(1));
		
		return rs.toString();
	}

	public boolean isExpressionResult() {
		return expressionResult;
	}

	public void setExpressionResult(boolean expressionResult) {
		this.expressionResult = expressionResult;
	}
	
}