package com.ymca;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebAppBootLoader extends  SpringBootServletInitializer  {

	public static ApplicationContext applicationContext ;
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	SpringApplicationBuilder builder = application.sources(WebConfig.class);
    	
    	return builder ;
        
    }
    
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		//servletContext.addFilter("openSessionInViewFilter", OpenSessionInViewFilter.class);
		servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);
		//WSServletContextListener wsSerlvet = new WSServletContextListener() ;
		//servletContext.addListener(wsSerlvet);
		//servletContext.addListener(new ContextLoaderListener());
	}
	
/*	@Bean
	public ServletRegistrationBean jaxws() {
	    final ServletRegistrationBean jaxws = new ServletRegistrationBean(new WSSpringServlet(), "/portal/ws");
	    return jaxws;
	}*/
	

/*    @Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		servlet.setNamespace("FacilityBookingService");
		return new ServletRegistrationBean(servlet, "/ws/FacilityBookingService");
	}*/
}
