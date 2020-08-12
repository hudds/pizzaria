package br.com.pizzaria.model.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	
	
	@Override
	public BigDecimal convert(String source) {
		
		if(source.matches("[\\+-]?\\d+,\\d+")) {
			source =source.replaceAll(",", ".");
		}

		return new BigDecimal(source);
	}

}
