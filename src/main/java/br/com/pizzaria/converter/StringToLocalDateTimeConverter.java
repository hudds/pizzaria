package br.com.pizzaria.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;


public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

	private final DateTimeFormatter formatter;
	
	public StringToLocalDateTimeConverter() {
		this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	}
	
	public StringToLocalDateTimeConverter(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);	
	}
	
	@Override
	public LocalDateTime convert(String source) {
		return LocalDateTime.parse(source, formatter);
	}

}
