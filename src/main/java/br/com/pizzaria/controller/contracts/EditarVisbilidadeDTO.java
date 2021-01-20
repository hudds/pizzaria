package br.com.pizzaria.controller.contracts;

public class EditarVisbilidadeDTO<I> {
	
	private I id;
	private Boolean visivel;
	
	public I getId() {
		return id;
	}
	public void setId(I id) {
		this.id = id;
	}
	public Boolean getVisivel() {
		return visivel;
	}
	public void setVisivel(Boolean visivel) {
		this.visivel = visivel;
	}
	

}
