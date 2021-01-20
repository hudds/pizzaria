package br.com.pizzaria.model.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.pizzaria.model.Bebida;

public class BebidaFormDTOTest {
	@Test
	public void deveConstruir() {
		assertThat(new BebidaFormDTO()).isNotNull();
	}
	
	@Test
	public void dadoBebidaDeveConstruirComOsMesmosValores() {
		Bebida bebida = new Bebida();
		bebida.setId(64375);
		bebida.setTitulo("bebida");
		bebida.setValor(new BigDecimal("1.99"));
		BebidaFormDTO dto = new BebidaFormDTO(bebida);
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(bebida.getId());
		assertThat(dto.getTitulo()).isEqualTo(bebida.getTitulo());
		assertThat(dto.getValor()).isEqualTo(bebida.getValor());
	}
}
