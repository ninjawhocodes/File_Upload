package com.ninjawhocodes.fileupload.controller;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ninjawhocodes.fileupload.entity.FileUploadEO;
import com.ninjawhocodes.fileupload.services.FileUploadService;
import com.ninjawhocodes.fileupload.view.UploadFileResponse;


/**
 * @author NinjaWhoCodes
 *
 */
@RestController
public class FileUploadController {

	 @GetMapping("/test")
	 public String test() {
		 return "Test";
	 }
	 
	 	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	    @Autowired
	    private FileUploadService fileUploadService;

	    @PostMapping("/uploadFile")
	    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
	    	FileUploadEO dbFile = fileUploadService.storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(dbFile.getFileId())
	                .toUriString();

	        return new UploadFileResponse(dbFile.getName(), fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }

	    @PostMapping("/uploadMultipleFiles")
	    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	        return Arrays.asList(files)
	                .stream()
	                .map(file -> uploadFile(file))
	                .collect(Collectors.toList());
	    }

	    @GetMapping("/downloadFile/{fileId}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
	    	FileUploadEO dbFile = fileUploadService.getFile(fileId);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(dbFile.getType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getName() + "\"")
	                .body(new ByteArrayResource(dbFile.getData()));
	    }

}
