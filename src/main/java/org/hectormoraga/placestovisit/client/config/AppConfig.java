package org.hectormoraga.placestovisit.client.config;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type = HypermediaType.HAL)
@ComponentScan("org.hectormoraga.placestovisit.client")
@PropertySource({ "classpath:application.properties" })
public class AppConfig implements WebMvcConfigurer {

	// define a bean for ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	// add resource handler for loading css, images, etc	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/"); 
    }
    
	// define bean for RestTemplate ... this is used to make client REST calls
	@Bean
	RestTemplate restTemplate(HypermediaRestTemplateConfigurer configurer) {
	  return configurer.registerHypermediaTypes(new RestTemplate());
	}
	
	@Bean 
	RestTemplateCustomizer hypermediaRestTemplateCustomizer(HypermediaRestTemplateConfigurer configurer) { 
	    return configurer::registerHypermediaTypes;
	}
}
