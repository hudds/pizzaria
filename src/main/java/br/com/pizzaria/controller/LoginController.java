package br.com.pizzaria.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(method = RequestMethod.GET, path = {"/login"})
	public ModelAndView formLogin(@RequestParam(required = false, name = "status") String status, Authentication authentication) {
		if(authentication != null && authentication.isAuthenticated()) {
			return new ModelAndView("redirect:/");
		}
		ModelAndView modelAndView = new ModelAndView("login/formLogin");
		modelAndView.addObject("status", status);
		return modelAndView;
	}
	
}
