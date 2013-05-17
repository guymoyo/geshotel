package org.adorsys.geshotel.web;

import javax.validation.Valid;

import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/passwordresets")
public class PasswordResetController {

	@RequestMapping(params={"form"}, method=RequestMethod.GET)
	public String passwordResetForm(Model uiModel){
		PasswordReset passwordReset = new PasswordReset();
//		Account account = SecurityUtil.getAccount();
		UserAccount account = SecurityUtil.getBaseUser();
		passwordReset.setUserName(account.getIdentifier());
		uiModel.addAttribute("passwordReset", passwordReset);
		return "passwordresets/form";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String passwordReset(@Valid PasswordReset passwordReset, BindingResult bindingResult, Model uiModel){
        if(!passwordReset.sontPasswordEgaux()){
	        // Code Review
	       // TODO: What about i18N
        	ObjectError error = new ObjectError("newPassword","les deux passwords ne sont pas identique");
			bindingResult.addError(error);
        }

//        Account account = SecurityUtil.getAccount();
        UserAccount account = SecurityUtil.getBaseUser();
        if(!account.checkExistingPasword(passwordReset.getCurrentPassword())){
	        // Code Review
	       // TODO: What about i18N
        	ObjectError error = new ObjectError("currentPassword","le password actuel n'est pas correct");
			bindingResult.addError(error);
        }
        
    	if (bindingResult.hasErrors()) {
    		passwordReset.setUserName(account.getIdentifier());// doesn't come back
            uiModel.addAttribute("passwordReset", passwordReset);
            return "passwordresets/form";
        }
        uiModel.asMap().clear();
        
        account.changePassword(passwordReset.getNewPassword());
        account = account.merge();

		return "passwordresets/response";
	}
}
