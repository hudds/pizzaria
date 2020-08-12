package br.com.pizzaria.controller.util;

public class EnderecoEContatoFormParams {
	
	private String redirectAfter;
	private FormIntention intention;
	
	public String getRedirectAfter() {
		return redirectAfter;
	}
	public void setRedirectAfter(String redirectAfter) {
		this.redirectAfter = redirectAfter;
	}
	public FormIntention getIntention() {
		return intention;
	}
	public void setIntention(FormIntention intention) {
		this.intention = intention;
	}

}

