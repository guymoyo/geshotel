package org.adorsys.geshotel.web.ext;

import javax.annotation.Resource;

import org.adorsys.geshotel.domain.DataInitializerHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ApplicationInitService {
	@Resource
	@Qualifier("dataInitializerHelper")
	private DataInitializerHelper dataInitializerHelper;
	public void initApplication(){
		dataInitializerHelper.initApplication();
	}
}