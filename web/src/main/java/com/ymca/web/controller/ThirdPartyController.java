package com.ymca.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThirdPartyController extends BaseController {
	
		
	@RequestMapping(value="/thirdPartyUI", method=RequestMethod.GET)
    public String generateInvoice(@RequestParam String signUpId, @RequestParam String payerId) { 
        return "thirdPartyUI";
    }

}
