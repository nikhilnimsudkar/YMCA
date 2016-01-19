package com.serene.job.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.ymca.core.service.HallPassService;
import com.ymca.dao.UserDao;
import com.ymca.model.User;

@Service
@ComponentScan("com.ymca.core.service")
public class HallpassItemProcessor extends GenericItemProcessor implements ItemProcessor<Object,Object>{
	
	private static final Log log = LogFactory.getLog(HallpassItemProcessor.class);
	
	@Autowired
	private HallPassService hallPassService;
	
	@Override
	public Object process(Object item) throws Exception {
		
		Map<String,Object> data = (Map<String, Object>) item;
		
		Boolean isSexOffender = false;
		String cId = data.get("contactId").toString();
		
		if(cId!=null && !"".equals(cId)){
			isSexOffender = hallPassService.isSexOffender(data);
			
			if(isSexOffender){
				String agentId = "";
				List<String> offenderIds = hallPassService.getOffenderIds(data,agentId);
				
				if(offenderIds.size()==0)
					return null;
				
				List<Map> childMapData = new ArrayList<Map>();
				for(String offenderId: offenderIds){
					/*
					data.put("offenderId_c", offenderId);
					data.put("noteType", "SEXOFFENDER");
					data.put("isSexOffender", true);
					data.put("noteDescription", "");
					*/
					Map<String,Object> childData = new HashMap<String,Object>();
					childData.put("contactId", cId);
					childData.put("offenderId", offenderId);
					childData.put("noteType", "SEXOFFENDER");
					childData.put("isSexOffender", true);
					//childData.put("noteDescription", "");
					
					childMapData.add(childData);
				}
				data.put("offenderIdsCollection", childMapData);
				data.put("isSexOffender", "Yes");
			}
		}
		return data;
	}
}
