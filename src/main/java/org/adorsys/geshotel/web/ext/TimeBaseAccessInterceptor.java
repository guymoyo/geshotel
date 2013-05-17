package org.adorsys.geshotel.web.ext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
@Service
public class TimeBaseAccessInterceptor implements HandlerInterceptor  {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Checking Time ......");
		UserAccount baseUser = SecurityUtil.getBaseUser();
		if(baseUser == null) {
			System.out.println("No user found");
			return true;
		}
		PeriodConf periodConf = baseUser.getPeriodConf();
		if(periodConf == null ) return false;
		
		if(checkPeriod(periodConf)){
			System.out.println("you are ready to work");
			return true;
		}else{
			response.sendRedirect("/geshotel/notallowed");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Post Handle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("Pre Handle");
		
	}
	
	public boolean checkPeriod(PeriodConf period){
		int openingTime = period.getOpeningTime();
		int closingTime = period.getClosingTime();
		int currentHour = getCurrentHour();
		if((openingTime <= currentHour) && (currentHour < closingTime)) return true;
		return false;
	}
	
	public int getCurrentHour(){
		DateTime dateTime = new DateTime();
		int hourOfDay = dateTime.getHourOfDay();
		System.out.println("Hours "+hourOfDay);
		return hourOfDay;
	}
}
