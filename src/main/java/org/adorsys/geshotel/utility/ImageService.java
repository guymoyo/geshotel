package org.adorsys.geshotel.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class have methods to process some operations on images.
 */
public class ImageService {

	// TODO: Avoid hardcoded file separator (ex.: "/") declaration.
	// Use the separator property of the File-Object (File.separator) instead.

	// Code Review
	// TODO: Avoid hardcoded file separator (ex.: "/") declaration.
	// Use the separator property of the File-Object (File.separator) instead.
	// Think of System-independence

	public static final String ROOT_DIR = "/tools/logo";
	
	public static void deleteFiles(Long listingsId) {
		File fileDIr = new File(ROOT_DIR + listingsId);
		try {
			FileUtils.forceDelete(fileDIr);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static void deleteFiles(Long listingsId, String fileName) {
		File fileDIr = new File(ROOT_DIR + listingsId + "/" + fileName);
		try {
			FileUtils.forceDelete(fileDIr);
		} catch (IOException e) {
			// throw new IllegalStateException(e);
		}
	}

	/**
	 * Returns the name of the files saved. 0 for small 1 for medium
	 * 
	 * @param file
	 * @param listingId
	 * @return
	 * @throws IOException
	 */
	public static String storeFile(MultipartFile file, Long listingId)
			throws IOException {
		Contract.notNull("File", file);

		String originalFilename = file.getOriginalFilename();
		String contentType = file.getContentType();
		if (StringUtils.isBlank(contentType)) {
			if (StringUtils.isBlank(originalFilename)) {
				return null;
			}
			String fileExtention = FilenameUtils.getExtension(originalFilename);
			// contentType = MscFileUtils.getContentType(fileExtention);
			if (StringUtils.isBlank(contentType)) {
				return null;
				// Code Review
				// TODO: This is not the right exception to throw here
				// No I/O-Operation
			}
		}
		String fileName = null;
		if (StringUtils.isNotBlank(originalFilename)) {
			String fileExtension = "jpg";
			fileName = RandomStringUtils.randomAlphabetic(6);
				fileName += "." + fileExtension;
			
		}
		long fileSize = file.getSize();
		File fileDIr = new File( "./src/main/webapp/images/");
		/*File fileDIr = new File(ImageService.ROOT_DIR);*/
		File diskFile = new File(fileDIr, fileName);
		
		FileOutputStream fileOutputStream = new FileOutputStream(diskFile);
		IOUtils.copy(file.getInputStream(), fileOutputStream);
		IOUtils.closeQuietly(fileOutputStream);

		// THumbnail
     return fileName;
	}

	

}
