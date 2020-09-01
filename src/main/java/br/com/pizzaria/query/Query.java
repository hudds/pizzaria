package br.com.pizzaria.query;

import java.util.Map;

public interface Query {
	
	public String createJPQL();
	public Map<String,Object> getNamedParameters();

}
