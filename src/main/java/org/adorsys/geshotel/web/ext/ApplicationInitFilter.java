package org.adorsys.geshotel.web.ext;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.geshotel.domain.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Used to initialize the application, creating the defaul user, aso.
 *
 */
@Service("applicationInitFilter")
public class ApplicationInitFilter extends OncePerRequestFilter {

	@Resource
	private ApplicationInitService applicationInitService;
	
	private boolean initialized;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!initialized && UserAccount.countUserAccounts()<1){
			applicationInitService.initApplication();
			initialized = true;
		}
		filterChain.doFilter(request, response);
	}

}
