package br.com.pizzaria.security.util;

import org.springframework.security.core.Authentication;

public class RoleChecker {
	
	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	public static boolean currentUserIsAdmin(Authentication authentication) {
		System.out.println("Authentication: " + authentication);
		if(authentication != null) {
			System.out.println("authorities: " + authentication.getAuthorities());
		}
		return authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN));
	}

}
