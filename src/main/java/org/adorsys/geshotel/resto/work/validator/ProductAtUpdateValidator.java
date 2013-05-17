package org.adorsys.geshotel.resto.work.validator;

import org.adorsys.geshotel.resto.domain.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProductAtUpdateValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class == clazz;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		
	}

}
