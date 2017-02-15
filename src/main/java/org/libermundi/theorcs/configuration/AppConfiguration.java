package org.libermundi.theorcs.configuration;
import java.util.Properties;

import org.libermundi.theorcs.exceptions.TheOrcsSimpleMappingExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
 
@Configuration
public class AppConfiguration{

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new TheOrcsSimpleMappingExceptionResolver();
		
		Properties mappings = new Properties();
		
		mappings.setProperty("UserNotFoundException", "error/404");
		
		mappings.setProperty("RuntimeException", "error/500");
		
		r.setExceptionMappings(mappings);
		r.setOrder(-1);
		
		return r;
		
	}
	
}