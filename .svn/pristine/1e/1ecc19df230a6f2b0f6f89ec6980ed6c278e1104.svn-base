package com.ymca.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping("/shopping")
public class CartController {
	
	@RequestMapping(value="/cart", method=RequestMethod.GET)
    public String viewCart(final HttpServletRequest request, final HttpServletResponse response) { 
        return "cart";
    }
}
