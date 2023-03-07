package br.com.pizzaria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import br.com.pizzaria.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

	@Autowired
	private UsuarioService usuarioService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8");
		http.addFilterBefore(filter, CsrfFilter.class);
		http.authorizeRequests()
				.antMatchers("/usuarios/cadastro/**")
				.permitAll()
				.antMatchers("/usuarios/info/**")
				.authenticated()
				.antMatchers("/usuarios/menu/**")
				.authenticated()
				.antMatchers("/usuarios/**")
				.hasRole("ADMIN")

				.antMatchers("/sabor/cadastro/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/edit/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/json/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/esconde/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/mostra/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/visivel/**")
				.hasRole("ADMIN")

				.antMatchers("/bebida/lista/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/json/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/edit/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/visivel/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/cadastro/**")
				.hasRole("ADMIN")

				.antMatchers("/pedido/lista/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/recebidos/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/preparando/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/json/lista**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/enviado/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/endereco/**")
				.authenticated()
				.antMatchers("/pedido/pagamento/**")
				.authenticated()
				.antMatchers("/pedido/resumo/**")
				.authenticated()
				.antMatchers("/pedido/confirma")
				.authenticated()
				.antMatchers("/pedido/detalhes/{id}/**")
				.authenticated()
				.antMatchers("/pedido/detalhes/{id}/**")
				.access("@checkPedidoUserId.check(authentication, #id)")

				.antMatchers("/pizza/cadastro/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/edit/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/json/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/visivel/**")
				.hasRole("ADMIN")
				.antMatchers("/*")
				.permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.failureUrl("/login?status=bad_credentials")
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/");
		return http.build();
	}

	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8");
		http.addFilterBefore(filter, CsrfFilter.class);
		http.authorizeRequests()
				.antMatchers("/usuarios/cadastro/**")
				.permitAll()
				.antMatchers("/usuarios/info/**")
				.authenticated()
				.antMatchers("/usuarios/menu/**")
				.authenticated()
				.antMatchers("/usuarios/**")
				.hasRole("ADMIN")

				.antMatchers("/sabor/cadastro/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/edit/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/json/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/esconde/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/mostra/**")
				.hasRole("ADMIN")
				.antMatchers("/sabor/visivel/**")
				.hasRole("ADMIN")

				.antMatchers("/bebida/lista/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/json/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/edit/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/visivel/**")
				.hasRole("ADMIN")
				.antMatchers("/bebida/cadastro/**")
				.hasRole("ADMIN")

				.antMatchers("/pedido/lista/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/recebidos/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/preparando/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/json/lista**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/enviado/**")
				.hasRole("ADMIN")
				.antMatchers("/pedido/endereco/**")
				.authenticated()
				.antMatchers("/pedido/pagamento/**")
				.authenticated()
				.antMatchers("/pedido/resumo/**")
				.authenticated()
				.antMatchers("/pedido/confirma")
				.authenticated()
				.antMatchers("/pedido/detalhes/{id}/**")
				.authenticated()
				.antMatchers("/pedido/detalhes/{id}/**")
				.access("@checkPedidoUserId.check(authentication, #id)")

				.antMatchers("/pizza/cadastro/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/edit/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/json/delete/**")
				.hasRole("ADMIN")
				.antMatchers("/pizza/visivel/**")
				.hasRole("ADMIN")
				.antMatchers("/*")
				.permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.failureUrl("/login?status=bad_credentials")
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/");
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Autowired
	void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(this.usuarioService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
