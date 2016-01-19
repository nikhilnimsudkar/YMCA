package com.ymca.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymca.model.Signup;
import com.ymca.web.service.PromoExpressionEvaluator;

@Controller
public class TestController extends BaseController {

	private Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Resource
	public PromoExpressionEvaluator promoExpressionEvaluator;
	
/*	@Resource(name="rollbackTransactionManager")
	JpaTransactionManager rollbackTransactionManager;
	*/
	@RequestMapping(value="testMethod", method=RequestMethod.GET)
    public String testMethod() {
		
		
		log.info("  TestController.testMethod() ....  ");
		
//		TransactionTemplate template = new TransactionTemplate(rollbackTransactionManager);

    	/*template.execute(new TransactionCallback<Object>() {
    	  public Object doInTransaction(TransactionStatus transactionStatus) {
    		  //ALL YOUR CODE ARE BELONG TO... SINGLE TRANSACTION
    	*/  
    		  	String sqlExpression = "select 1 from signup";
    			
    		  	List<Signup> cartSignups = new ArrayList<Signup>(); 
    		  	
    			Signup signup = new Signup();
    			signup.setItemDetailId(Long.valueOf("300000003600476"));
    			signup.setProgramStartDate(new Date());
    			
    			cartSignups.add(signup);
    			
    			evaluatePromotionExpression(sqlExpression, signup, cartSignups);
    			
    		 // return null;
    	/*  }
    	});
		*/
		/*try {
			
			Object obj = MethodUtils.invokeMethod(signup, "programStartDate", null);
			
			log.info("  TestController.testMethod() >>>>   "+obj.toString());
			
			
		} catch (NoSuchMethodException | IllegalAccessException
				| InvocationTargetException e) {
			e.printStackTrace();
		}*/
		
        return "login";
    }
	
	public boolean evaluatePromotionExpression(String sqlExpression, Signup signup, List<Signup> cartSignups){
		//try {
			
			promoExpressionEvaluator.evaluatePromotionExpressionTransactional(sqlExpression, signup, cartSignups);
		
			log.info("   promoExpressionEvaluator.expressionResult  >>>  "+promoExpressionEvaluator.expressionResult); 
			
		/*} catch (Exception e) {
			log.error(" Exception For Rollback :::  "+e.getMessage());
			e.printStackTrace();
		}*/
		
		return promoExpressionEvaluator.expressionResult;
	}
	
}
