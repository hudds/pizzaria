package br.com.pizzaria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class LoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveRetornarFormularioDeLogin() throws Exception {
		mockMvc.perform(get("/login")).andExpect(forwardedUrl("/WEB-INF/jsp/login/formLogin.jsp"));
	}
	
	@Test
	@WithMockUser
	public void deveRedirecionarParaAHome() throws Exception {
		mockMvc.perform(get("/login")).andExpect(redirectedUrl("/"));
	}
	
}
