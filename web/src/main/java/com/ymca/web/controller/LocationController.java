package com.ymca.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymca.dao.LocationDao;
import com.ymca.model.Location;

@Controller
@RequestMapping("/location")
public class LocationController {	
	private static Logger log =  Logger.getLogger(LocationController.class);	
	
	@Resource
	private LocationDao locationDao;
	
	@RequestMapping(value="/getYLocations", method=RequestMethod.GET)
    public @ResponseBody List<Location>  getYLocations(final HttpServletRequest request, final HttpServletResponse response) { 		
    	try{
    		List<Location> locationLst =  locationDao.findByStatusOrderByRecordNameAsc("Active");
    		return locationLst; 	    	  					
    	}catch(Exception e){    		
    		log.error("Error occured while querying locations ", e);        		
    	}
		return null;    	
    }

}
