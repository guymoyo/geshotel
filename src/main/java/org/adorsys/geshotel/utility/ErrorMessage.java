/**
 * 
 */
package org.adorsys.geshotel.utility;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author guy moyo
 * get message in fr or en
 */
public class ErrorMessage {
	
	private String messageFr;
	
	private String messageEn;
	
	private String lang =lang();
	
	private String lang() {
		Locale locale = LocaleContextHolder.getLocale();
		return locale.getLanguage();
	}
	
	public String getMessage(){
		if(this.lang.compareToIgnoreCase("fr")==0)
			return this.messageFr;
		else
			return this.messageEn;
		
	}

	public String getMessageFr() {
		return messageFr;
	}

	public void setMessageFr(String messageFr) {
		this.messageFr = messageFr;
	}

	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public ErrorMessage(String messageFr, String messageEn) {
		super();
		this.messageFr = messageFr;
		this.messageEn = messageEn;
	}
	
	
}
