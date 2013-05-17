package org.adorsys.geshotel.domain.security;

import javax.persistence.TypedQuery;

import org.adorsys.geshotel.domain.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

	public static UserDetails getUserDetails(){
		SecurityContext context = SecurityContextHolder.getContext();
		if(context==null) return null;
		Authentication authentication = context.getAuthentication();
		if(authentication==null) return null;
		Object principal = authentication.getPrincipal();
		if(principal==null) return null;
		if (principal instanceof UserDetails) {
		  return ((UserDetails)principal);
		} else {
		  return null;
		}
	}
	
	public static String getUserName(){
		UserDetails userDetails = getUserDetails();
		if(userDetails!=null) return userDetails.getUsername();
		return null;
	}
	
	public static UserAccount getBaseUser(){
		UserDetails userDetails = getUserDetails();
		if(userDetails==null) return null;
		String identifier = userDetails.getUsername();
		TypedQuery<UserAccount> query = UserAccount.findUserAccountsByIdentifierEquals(identifier);
		return query.getSingleResult();
	}

}
