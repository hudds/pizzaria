package br.com.pizzaria.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.pizzaria.model.Bebida;
import br.com.pizzaria.model.dto.BebidaFormDTO;
import br.com.pizzaria.service.BebidaService;
import br.com.pizzaria.test.util.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class BebidaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BebidaService bebidaService;
	
	
	@Test
	public void acessarFormularioCadastroSemAutenticacaoDeveRetornarFormularioDeLogin() throws Exception {
		this.mockMvc.perform(get("/bebida/cadastro"))
		.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(roles = {"CLIENTE"})
	public void acessarFormularioCadastroSemAutorizacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(get("/bebida/cadastro"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void acessarAutorizadoDeveRetornarFormularioCadastro() throws Exception {
		this.mockMvc.perform(get("/bebida/cadastro"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"));
	}
	
	@Test
	public void cadastrarSemAutenticacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(post("/bebida/cadastro"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"CLIENTE"})
	public void cadastrarSemAutorizacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(post("/bebida/cadastro"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveCadastrar() throws Exception {
		int qntBebidasAntes = bebidaService.buscaBebidas().size();
		System.out.println(bebidaService.buscaBebidas());
		this.mockMvc.perform(post("/bebida/cadastro").params(getParametrosBebidaValida()).with(csrf()))
		.andExpect(redirectedUrlPattern("**/lista"));
		int qntBebidasDepois = bebidaService.buscaBebidas().size();
		System.out.println(bebidaService.buscaBebidas());
		assertThat(qntBebidasDepois).isEqualTo(qntBebidasAntes+1);
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void comValoresInvalidosDeveRetornarFormularioCadastro() throws Exception {
		this.mockMvc.perform(post("/bebida/cadastro").param("titulo", "").with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"));
		
		this.mockMvc.perform(post("/bebida/cadastro")
				.param("titulo", " ")
				.param("valor", "1.99")
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("novaBebida", "titulo"));
		
		this.mockMvc.perform(post("/bebida/cadastro")
				.param("titulo", "")
				.param("valor", "1.99")
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("novaBebida", "titulo"));
		
		this.mockMvc.perform(post("/bebida/cadastro")
				.param("valor", "1.99")
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("novaBebida", "titulo"));
		
		this.mockMvc.perform(post("/bebida/cadastro")
				.param("titulo", "bebida")
				.param("valor", "")
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("novaBebida", "valor"));
		
		this.mockMvc.perform(post("/bebida/cadastro")
				.param("titulo", "bebida")
				.param("valor", " ")
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("novaBebida", "valor"));
		
		this.mockMvc.perform(post("/bebida/cadastro")
				.param("titulo", "bebida")
				.param("valor", "-1")
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("novaBebida", "valor"));
		
		this.mockMvc.perform(post("/bebida/cadastro")
				.param("titulo", "bebida")
				.param("valor", "0")
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formCadastroBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("novaBebida", "valor"));
	}
	

	@Test
	public void acessarListaSemAutenticacaoDeveRetornarFormularioDeLogin() throws Exception {
		this.mockMvc.perform(get("/bebida/lista"))
		.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(roles = {"CLIENTE"})
	public void acessarListaSemAutorizacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(get("/bebida/lista"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void acessarAutorizadoDeveRetornarListaDeBebidas() throws Exception {
		Bebida bebida = TestUtils.criaBebida();
		bebidaService.grava(bebida);
		
		List<Bebida> bebidas = Arrays.asList(bebida);
		
		this.mockMvc.perform(get("/bebida/lista"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/listaBebidas.jsp"))
		.andExpect(model().attribute("bebidas", bebidas));
	}

	
	
	@Test
	public void acessarFormularioEdicaoSemAutenticacaoDeveRetornarFormularioDeLogin() throws Exception {
		this.mockMvc.perform(get("/bebida/edit/1"))
		.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(roles = {"CLIENTE"})
	public void acessarFormularioDeEdicaoSemAutorizacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(get("/bebida/edit/1"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void acessarAutorizadoDeveRetornarFormularioDeEdicao() throws Exception {
		Bebida bebida = TestUtils.criaBebida();
		bebidaService.grava(bebida);
		this.mockMvc.perform(get("/bebida/edit/1"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attribute("editBebida", TestUtils.criaBebidaDTO(bebida)));
	}
	
	@Test
	public void editarSemAutenticacaoDeveRetornarFormularioDeLogin() throws Exception {
		this.mockMvc.perform(post("/bebida/edit").with(csrf()))
		.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(roles = {"CLIENTE"})
	public void editarSemAutorizacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(post("/bebida/edit").with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveEditar() throws Exception {
		Bebida bebida = TestUtils.criaBebida();
		Integer id = bebidaService.grava(bebida);
		BebidaFormDTO bebidaEditada = new BebidaFormDTO();
		bebidaEditada.setTitulo(bebida.getTitulo() + 2);
		bebidaEditada.setValor(bebida.getValor().add(BigDecimal.ONE));
		bebidaEditada.setId(id);
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", bebidaEditada.getTitulo())
				.param("valor", bebidaEditada.getValor().toString())
				.param("id", bebidaEditada.getId().toString())
				.with(csrf()))
		.andExpect(redirectedUrlPattern("**/edit/"+id))
		.andExpect(flash().attribute("bebida_cadastro_status", "success"));
		
		bebida = bebidaService.buscaBebida(id);
		assertThat(bebida.getTitulo()).isEqualTo(bebidaEditada.getTitulo());
		assertThat(bebida.getValor()).isEqualTo(bebidaEditada.getValor());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void comValoresInvalidosNaoDeveEditar() throws Exception {
		Bebida bebida = TestUtils.criaBebida();
		Integer id = bebidaService.grava(bebida);
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", "")
				.param("valor","1")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "titulo"));
		
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", " ")
				.param("valor", "1")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "titulo"));
		
		this.mockMvc.perform(post("/bebida/edit")
				.param("valor", "1")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "titulo"));
		
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", "bebida")
				.param("valor", "")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "valor"));
		
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", "bebida")
				.param("valor", " ")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "valor"));
		
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", "bebida")
				.param("valor", "-1")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "valor"));
		
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", "bebida")
				.param("valor", "0")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "valor"));
		
		this.mockMvc.perform(post("/bebida/edit")
				.param("titulo", "bebida")
				.param("valor", "rrhsh")
				.param("id", id.toString())
				.with(csrf()))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/formEditBebida.jsp"))
		.andExpect(model().attributeHasFieldErrors("editBebida", "valor"));
		
		
	}
	
	@Test
	public void requisitarDeleteSemAutenticacaoDeveRetornarFormularioDeLogin() throws Exception {
		this.mockMvc.perform(get("/bebida/delete/1"))
		.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(roles = {"CLIENTE"})
	public void requisitarDeleteSemAutorizacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(get("/bebida/delete/1"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void requisitarDeleteAutorizadoDeveRetornarPaginaDeConfirmacao() throws Exception {
		Bebida bebida = TestUtils.criaBebida();
		Integer id = bebidaService.grava(bebida);
		bebida = bebidaService.buscaBebida(id);
		this.mockMvc.perform(get("/bebida/delete/"+id))
		.andExpect(forwardedUrl("/WEB-INF/jsp/bebida/confirmarDelete.jsp"))
		.andExpect(model().attribute("bebida", bebida));
	}
	
	@Test
	public void deletarSemAutenticacaoDeveRetornarFormularioDeLogin() throws Exception {
		this.mockMvc.perform(post("/bebida/delete/1").with(csrf()))
		.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(roles = {"CLIENTE"})
	public void deletarSemAutorizacaoDeveSerProibido() throws Exception {
		this.mockMvc.perform(get("/bebida/delete/1").with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void comAutorizacaoDeveDeletar() throws Exception {
		Bebida bebida = TestUtils.criaBebida();
		Integer id = bebidaService.grava(bebida);
		this.mockMvc.perform(post("/bebida/delete/"+id).with(csrf()))
		.andExpect(redirectedUrl("/bebida/lista"))
		.andExpect(flash().attribute("bebida_delete_status", "success"));
		List<Bebida> bebidas = bebidaService.buscaBebidas();
		assertThat(bebidas).doesNotContain(bebida);
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void deveRetornarJsonBebidas() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
		Bebida bebida = TestUtils.criaBebida();
		bebidaService.grava(bebida);
		String expectedJson = mapper.writeValueAsString(bebidaService.buscaBebidas());
		this.mockMvc.perform(get("/bebida/json"))
		.andExpect(status().isOk())
		.andExpect(content().json(expectedJson));
	}
	
	
	
	
	
	private MultiValueMap<String, String> getParametrosBebidaValida(){
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.put("titulo",  Arrays.asList("Guarana"));
		params.put("valor",  Arrays.asList("1.99"));
		return params;
	}
}
