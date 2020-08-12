package br.com.pizzaria;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import br.com.pizzaria.config.WebAppConfig;
import br.com.pizzaria.controller.HomeController;
import br.com.pizzaria.security.CheckPedidoUserId;

@ComponentScan(basePackageClasses = {WebAppConfig.class, HomeController.class, CheckPedidoUserId.class})
public class ServletInitializer extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PizzariaApplication.class);
	}
	
	

}
