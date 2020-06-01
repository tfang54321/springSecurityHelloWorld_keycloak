package com.boraji.tutorial.security.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { HelloWorldConfiguration.class };
	}
 

	
//	   @Override
//	   protected Class<?>[] getServletConfigClasses() {
//	      return new Class[] { SecurityConfiguration.class };
//	   }
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	
	  @Override
	   protected Class<?>[] getServletConfigClasses() {
	      return new Class[] { KeycloakConfig.class };
	   }
	
	

}
