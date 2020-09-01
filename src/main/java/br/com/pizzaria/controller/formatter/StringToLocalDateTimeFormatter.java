package br.com.pizzaria.controller.formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class StringToLocalDateTimeFormatter implements Formatter<LocalDateTime> {

	private final DateTimeFormatter formatter;
	
	public StringToLocalDateTimeFormatter() {
		formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	}
	
	public StringToLocalDateTimeFormatter(String pattern) {
		formatter = DateTimeFormatter.ofPattern(pattern);
	}
	
	@Override
	public String print(LocalDateTime object, Locale locale) {
		return object.toString();
	}

	@Override
	public LocalDateTime parse(String text, Locale locale) throws ParseException {
		if(text == null || text.trim().isEmpty()) {
			return null;
		}
		return LocalDateTime.parse(text, formatter);
		
	}

	

}
