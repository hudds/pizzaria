package br.com.pizzaria.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.pizzaria.model.Endereco;
import br.com.pizzaria.model.EstadoPedido;
import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.util.Reflections;
import br.com.pizzaria.util.StringUtil;

public class PedidoQuery implements Query{
	
	private Integer id;
	private LocalDateTime firstDate;
	private LocalDateTime secondDate;
	private EstadoPedido estadoPedido;
	private Endereco endereco;
	private Usuario cliente;
	private final Map<String,Object> parameters = new HashMap<>();
	
	public LocalDateTime getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(LocalDateTime firstDate) {
		this.firstDate = firstDate;
	}
	public LocalDateTime getSecondDate() {
		return secondDate;
	}
	public void setSecondDate(LocalDateTime secondDate) {
		this.secondDate = secondDate;
	}
	public EstadoPedido getEstadoPedido() {
		return estadoPedido;
	}
	public void setEstadoPedido(EstadoPedido estadoPedido) {
		this.estadoPedido = estadoPedido;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Usuario getCliente() {
		return cliente;
	}
	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Map<String, Object> getNamedParameters(){
		return new HashMap<String,Object>(parameters);
	}
	
	@Override
	public String createJPQL() {
		parameters.clear();
		String jpql = "select p from Pedido p";
		if(estadoPedido != null) {
			jpql += " where p.estado = :pEstado";
			parameters.put("pEstado", this.estadoPedido);
		}
		
		if(firstDate != null && secondDate != null) {
			jpql += " and p.horaPedido between :pFirstDate and :pSecondDate";
			parameters.put("pFirstDate", this.firstDate);
			parameters.put("pSecondDate", this.secondDate);
		} else if(firstDate != null) {
			jpql += " and p.horaPedido between :pFirstDate and :pSecondDate";
			parameters.put("pFirstDate", this.firstDate.minusSeconds(this.firstDate.getSecond()));
			this.secondDate = this.firstDate.minusSeconds(this.firstDate.getSecond()).plusSeconds(60);
			parameters.put("pSecondDate", this.secondDate);
		} else if(secondDate != null) {
			this.firstDate = this.secondDate;
			jpql += " and p.horaPedido between :pFirstDate and :pSecondDate";
			parameters.put("pFirstDate", this.firstDate.minusSeconds(this.secondDate.getSecond()));
			this.secondDate = this.firstDate.minusSeconds(this.firstDate.getSecond()).plusSeconds(60);
			parameters.put("pSecondDate", this.secondDate);
		}
		
		if (endereco != null) {
			List<Field> nonNullDeclaredFields = Reflections.getNonNullDeclaredFields(endereco);
			for(Field field : nonNullDeclaredFields) {
				try {
					Object parameter = Reflections.getterFrom(endereco.getClass(), field).invoke(endereco);
					if(parameter.getClass().equals(String.class)) {
						if(((String) parameter).isEmpty()) {
							continue;
						}
					}
					String fieldName = field.getName();
					String parameterIdentifier = "pEndereco" + StringUtil.capitalizeFirstLetter(field.getName());
					jpql += String.format(" and p.endereco.%s = :%s", fieldName, parameterIdentifier);
					this.parameters.put(parameterIdentifier, parameter);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// intentional empty catch block
				}
			}
			
		}
		return jpql+=" order by p.id";
	}
	
	
}
