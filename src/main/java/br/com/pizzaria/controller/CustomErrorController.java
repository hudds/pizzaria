package br.com.pizzaria.controller;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping(path = { "/error" })
	public String handleError(HttpServletRequest httpRequest) {		
		String statusCode = httpRequest.getAttribute(ERROR_STATUS_CODE).toString();
		String returnValue = "error/error";
		Set<String> mappedPages = new HashSet<String>();
		mappedPages.addAll(Arrays.asList("404","403"));
		if(mappedPages.contains(statusCode)) {
			returnValue = "error/"+statusCode;
		}
		return returnValue;
	}
	
	


	public String getErrorPath() {
		return "/error";
	}

}
