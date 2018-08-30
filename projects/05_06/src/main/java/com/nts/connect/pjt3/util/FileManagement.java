package com.nts.connect.pjt3.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nts.connect.pjt3.dto.FileInfo;

/**
 * @author	유재찬
 * @date	2018. 8. 28.
 */
@Component("FileManagement")
@PropertySource("classpath:properties/application.properties")
public class FileManagement {
	@Value("${root.path}")
	private String filePath;

	public void saveFiles(List<MultipartFile> imageFiles, FileInfoExtractor fie) throws IOException {

		if (imageFiles == null) {
			return;
		}

		for (MultipartFile imageFile : imageFiles) {
			String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))
				+ imageFile.getOriginalFilename();
			String saveFileName = filePath + fileName;

			try (
				FileOutputStream fos = new FileOutputStream(saveFileName);
				InputStream is = imageFile.getInputStream();) {

				int readCount = 0;
				byte[] buffer = new byte[1024];
				while ((readCount = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readCount);
				}

				FileInfo fileInfo = new FileInfo();
				fileInfo.setContentType(imageFile.getContentType());
				fileInfo.setFileName(fileName);
				fileInfo.setSaveFileName(saveFileName);
				fileInfo.setDeleteFlag(0);

				fie.putFileInfo(fileInfo);
			}
		}
	}

}
