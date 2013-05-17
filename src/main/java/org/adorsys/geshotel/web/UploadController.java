package org.adorsys.geshotel.web;

import java.io.IOException;

import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.utility.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class UploadController {
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("itemId") String itemId,
			@RequestParam("fileData") MultipartFile file) {

		String id = itemId.replace(",", " ").trim();
		Long listingId = new Long(id);
		if (!file.isEmpty()) {
			
				try {
					String storeFile = ImageService.storeFile(file, listingId);
					Hotel hotel = Hotel.findHotel(listingId);
					hotel.setLogo(storeFile);
					hotel.merge();
					hotel.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			return "redirect:/hotels/"+id;
	}

	

}
