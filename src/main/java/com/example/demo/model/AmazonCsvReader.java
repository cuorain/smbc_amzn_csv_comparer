package com.example.demo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AmazonCsvReader {
	private AmazonCsvReader() {
		
	}
	
	public static List<String[]> read(final MultipartFile file){
		final List<String[]> readData = new ArrayList<String[]>();
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){
			String line;
			while((line = br.readLine()) != null) {
				// ２重ダブルクオーテーションになっていることがある
				String[] data = line.replaceAll("\"", "").split(",", -1);
				if(!isOrderData(data)) {continue;}
				readData.add(data);
			}
		}catch(IOException ex) {
			//TODO:例外処理
			ex.printStackTrace();
		}
		return readData;
	}

	private static boolean isOrderData(final String[] data) {
		try{
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		    // 厳密にチェック
		    sdf.setLenient(false);
		    // ２重ダブルクオーテーションになっていることがある
		    sdf.parse(data[0].replace("\"", ""));
		    return true;
		 
		  }catch(Exception ex){
		    return false;
		  }
	}
}
