package com.ymca;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.web.util.Constants;
import com.ymca.web.util.PropFileUtil;

public class URLInterceptor implements HandlerInterceptor{

	public static Logger log = LoggerFactory.getLogger(URLInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//System.out.println("---Before Method Execution---");
		
		/*System.out.println("  RequestURL  ::  "+request.getRequestURL());
		System.out.println("  PathInfo  ::  "+request.getPathInfo());
		System.out.println("  QueryString  ::  "+request.getQueryString());
		System.out.println("  RequestURI  ::  "+request.getRequestURI());
		System.out.println("  ContextPath  ::  "+request.getContextPath());
		System.out.println("  ServletPath  ::  "+request.getServletPath());
		System.out.println("  ServerName  ::  "+request.getServerName());
		System.out.println("  ServerPort  ::  "+request.getServerPort());
		System.out.println("  Protocol  ::  "+request.getProtocol());
		System.out.println("  Scheme  ::  "+request.getScheme());*/
		
		if(request.getServletPath().equals("/psu")){
			
			String textEncryptorPassword = PropFileUtil.getValue(Constants.TEXT_ENCRYPTOR_PASSWORD);
			//System.out.println("  textEncryptorPassword  "+textEncryptorPassword);
			
			String encTxt = request.getQueryString();
			
			BasicTextEncryptor txtEnc = new BasicTextEncryptor();
			
			txtEnc.setPassword(textEncryptorPassword);
			String queryString = txtEnc.decrypt(encTxt);
			log.debug("  URLInterceptor queryString   ::   "+queryString);
			//System.out.println(" queryString "+queryString);
			//System.out.println(" sendRedirect :: "+request.getContextPath()+request.getServletPath()+"?"+queryString);
			response.sendRedirect(request.getContextPath()+"/promoSignup"+"?"+queryString);
			//System.out.println(" sendRedirected ");
		}
		return true;
	}
	@Override
	public void postHandle(	HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//System.out.println("---method executed---");
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//System.out.println("---Request Completed---");
	}
	
}
