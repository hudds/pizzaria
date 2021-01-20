package br.com.pizzaria.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.github.benmanes.caffeine.cache.Caffeine;

import br.com.pizzaria.controller.HomeController;
import br.com.pizzaria.converter.StringToBigDecimalConverter;
import br.com.pizzaria.converter.StringToRoleConverter;
import br.com.pizzaria.dao.UsuarioDAO;

@Configuration
@EnableWebMvc
@EnableCaching
@ComponentScan(basePackageClasses = { HomeController.class, UsuarioDAO.class })
public class WebAppConfig implements WebMvcConfigurer {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToRoleConverter());
		registry.addConverter(new StringToBigDecimalConverter());
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(60);
		return messageSource;
	}

	@Bean
	public ViewResolver viewResolver() {
		return new InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
    public CacheManager cacheManager() {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("pedidos");
		caffeineCacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES));
        return caffeineCacheManager;
    }
	

}
