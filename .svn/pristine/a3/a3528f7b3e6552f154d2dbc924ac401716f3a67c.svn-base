package com.serene.job.processor;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.serene.job.common.Constants;
import com.serene.job.service.CommonService;
import com.serene.job.service.ConfirmWaitlistedSignupService;
import com.serene.job.service.InvoiceService;
import com.serene.job.service.PaymentJobService;
import com.ymca.model.JetPayPayment;	

@Service
@ComponentScan("com.ymca.core.service")
public class AutomatedWaitlistProcessor extends GenericItemProcessor implements ItemProcessor<Object,Object>{
	
	private static final Log log = LogFactory.getLog(AutomatedWaitlistProcessor.class);
	
	@Resource
	private ConfirmWaitlistedSignupService confirmWaitlistedSignupService ;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object process(Object item) throws Exception {
		
		Map<String,Object> data = (Map<String, Object>) item;
		
		try {
			confirmWaitlistedSignupService.confirmWaitlistedSignup(data, batchJobContext);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error in automated waitlist processor ",e);
			return null;
		}
		
		return data;
	}
}
