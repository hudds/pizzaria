package br.com.pizzaria;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.pizzaria.PizzariaApplication;
import br.com.pizzaria.config.WebAppConfig;
import br.com.pizzaria.controller.HomeController;

@ComponentScan(basePackageClasses = {WebAppConfig.class, HomeController.class})
public class ServletInitializer extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PizzariaApplication.class);
	}
	
	

}
