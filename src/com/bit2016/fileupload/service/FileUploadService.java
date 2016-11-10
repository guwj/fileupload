package com.bit2016.fileupload.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	private static final String SAVE_PATH = "/upload";
	private static final String URL = "/gallery/assets/";
	
	public String restore(MultipartFile multipartFile){
		
		String url = "";
		
		try{
		
		if( multipartFile.isEmpty() == true ){
			System.out.println("음슴");
			return url;
		}
		
		String originalFileName = multipartFile.getOriginalFilename();
		String extName = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.length());
		String saveFileName = generateSaveFileName(extName);
		Long fileSize = multipartFile.getSize();
		
		System.out.println("########OriginalFileName : " + originalFileName);
		System.out.println("########ExtName : " + extName );
		System.out.println("########SaveFileName : " + saveFileName);
		System.out.println("########fileSize : " +fileSize);
		
		writeFile(multipartFile, saveFileName);
		
		url = URL + saveFileName;
		
		}catch(IOException e){
			// throw new UploadFileException("write file");
			// 로그 남기기
			throw new RuntimeException("write file");
		}
		return url;
	}
	
	// 업로드 해주는 메소드
	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException{
		byte[] fileData = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream( SAVE_PATH + "/" + saveFileName);
		fos.write(fileData);
		fos.close();
	}
	
	
	private String generateSaveFileName(String extName){
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		
		
		// upload file의 유일한 이름을 만드는 과정
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);
		
		return fileName;
	}
}

