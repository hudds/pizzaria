package br.com.pizzaria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveRetornarHomePage() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(forwardedUrl("/WEB-INF/jsp/home/index.jsp"));
	}
	
}
