package com.ninjawhocodes.fileupload.services;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ninjawhocodes.fileupload.entity.FileUploadEO;
import com.ninjawhocodes.fileupload.exception.FileStorageException;
import com.ninjawhocodes.fileupload.exception.MyFileNotFoundException;
import com.ninjawhocodes.fileupload.repository.FileUploadRepo;
/**
 * @author NinjaWhoCodes
 *
 */
@Service
public class FileUploadService {

	@Autowired
	private FileUploadRepo fileUploadRepo;
	
	public FileUploadEO storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			FileUploadEO dbFile = new FileUploadEO(file.getContentType(),fileName, file.getBytes(), new Date());

			return fileUploadRepo.save(dbFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}

	}
		public FileUploadEO getFile(String fileId) {
			return fileUploadRepo.findById(fileId)
					.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
		}
	}