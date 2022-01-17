package com.backend.app.service;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.backend.app.model.FileUploadTb;
import com.backend.app.repository.FileRepository;

@Service
public class FileService {
@Autowired
private FileRepository fileRepository;
@Autowired
private UserService userService;

	public FileUploadTb uploadFile(MultipartFile file) throws IOException {

	
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileUploadTb fileuploadtb = null;
		System.out.println(fileName);
	
		FileUploadTb saveFile = null;
		
		try{
		if(userService.getUserRole().equalsIgnoreCase("USER")){
			
			
			fileuploadtb = new FileUploadTb(fileName,null,userService.getUserName(),false, file.getBytes(),new Date(),file.getContentType(),false);
		}else if(userService.getUserRole().equalsIgnoreCase("ADMIN")){
			System.out.println("ADMIN");
			FileUploadTb filePresent=fileRepository.findByFileNameAndSignStatus(fileName);
			
			System.out.println("outside if"+filePresent.getUploadBy());

			System.out.println(fileName);
			
			
			fileuploadtb = new FileUploadTb(fileName,userService.getUserName(),filePresent.getUploadBy(),true,file.getBytes(),new Date(),file.getContentType(),false);
				fileRepository.deleteById(filePresent.getId());
			
		}
		
		saveFile=fileRepository.save(fileuploadtb);
		

		
		}
		catch(Exception e){
			System.out.println(e);

		}
		return saveFile;


		
	}

	public FileUploadTb updatesignedFile(String date) throws IOException {

		
		try{

		
		if(userService.getUserRole().equalsIgnoreCase("ADMIN")){

			fileRepository.updatesignstatus(date);
	
		}
		
		

		}
		catch(Exception e){
			System.out.println("EEEEEEE"+ e);

		}
	
		return null;
	}

	public Stream<FileUploadTb> fetchUnsignedFile() {
		Stream<FileUploadTb> file =null;
	
		if(userService.getUserRole().equalsIgnoreCase("USER")){
		
			 file = fileRepository.findAllByUploadByAndSignedStatus(userService.getUserName());

				}
		
		else if(userService.getUserRole().equalsIgnoreCase("ADMIN")){
		 
				file = fileRepository.findAllBySignedStatusAndUploadedBY();
		}
		return file;
	}
	public FileUploadTb fetchFile(Integer id) {
		
		// TODO Auto-generated method stub
		return fileRepository.findById(id).get();
	}

}
