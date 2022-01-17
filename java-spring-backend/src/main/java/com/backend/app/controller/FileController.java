package com.backend.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import com.backend.app.domain.FileUpload;
import com.backend.app.model.FileUploadTb;
import com.backend.app.service.FileService;
import com.backend.app.service.UserService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class FileController {
	
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
		String message = "";
		try {
			fileService.uploadFile(file);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}


	@PostMapping("/updateFiles")
		public ResponseEntity<?> updatesignedFile(@RequestParam("date") String date){
		try{
	
			if(userService.getUserRole().equalsIgnoreCase("ADMIN")){
				fileService.updatesignedFile(date);
				}
		
			}catch (Exception e){
			}
	return null;
	}
	
	
	
	
	@GetMapping("/fetchFiles")
	@Transactional(readOnly = true)
	public ResponseEntity<List<FileUpload>> fetchUnsignedFile(){
	

		List<FileUpload> files = null ;
		try{
		if(userService.getUserRole().equalsIgnoreCase("ADMIN")){
		 files = fileService.fetchUnsignedFile().map(dbFile -> {
			return new FileUpload(dbFile.getFilename(),dbFile.getId(),dbFile.getUploadBy(),dbFile.getUploadDateTime().toString(),
			dbFile.getUserStatus(),dbFile.getUploadBy());
	}).collect(Collectors.toList());
		}
		else if(userService.getUserRole().equalsIgnoreCase("USER")){
			 files = fileService.fetchUnsignedFile().map(dbFile -> {
					return new FileUpload(dbFile.getFilename(),dbFile.getId(),dbFile.getSignedBy(),dbFile.getUploadDateTime().toString(),dbFile.getUserStatus(),dbFile.getUploadBy()
							);
			}).collect(Collectors.toList());
		}

		return ResponseEntity.status(HttpStatus.OK).body(files);
		}catch(NullPointerException e){
			return ResponseEntity.status(HttpStatus.OK).body(files);
		}
	}
	//download afile
	  @GetMapping("/files/{id}")
	  public ResponseEntity<Resource> getFile(@PathVariable("id") String id) {

		FileUploadTb fileDB = fileService.fetchFile(Integer.valueOf(id));
	    String fileType=fileDB.getFileType();
	    
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFilename() + "\"").contentType(MediaType.parseMediaType(fileType))
	        .body(new ByteArrayResource(fileDB.getFileData()));
	  }
	

}
