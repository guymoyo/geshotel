package org.adorsys.geshotel.domain.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.geshotel.web.ext.TimeBaseAccessInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class HotelAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements LogoutSuccessHandler {
	
	@Resource
	private TimeBaseAccessInterceptor accessInterceptor;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println(	" pricipal "+authentication.getPrincipal().toString()+" creadential "+authentication.getCredentials().toString());
		//TODO : Store connection in a database.
		/*boolean isAllowed=true;
		try {
			isAllowed =accessInterceptor.preHandle(request, response, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(isAllowed) */
			super.onAuthenticationSuccess(request, response, authentication);
	}


	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if(authentication != null)
		System.out.println("Log out user" + authentication.getName()
				+ " credentatial " + authentication.getCredentials()
				+ " pricipal " + authentication.getPrincipal().toString());
		redirectUser(request, response, "/");
	}

	private void redirectUser(HttpServletRequest request,HttpServletResponse response,String additionalPaht) throws IOException{
		String contextPath = request.getContextPath();
		if(additionalPaht== null) additionalPaht = new String();
		response.sendRedirect(contextPath+additionalPaht);
	}
	
}


