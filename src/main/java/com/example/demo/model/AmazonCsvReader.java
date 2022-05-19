package com.example.demo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AmazonCsvReader{
	//判定用の日付列
	private static final int dateIndex = 0;
	private final CsvReader csvReader = new CsvReader();
	
	public AmazonCsvReader() {
		
	}
	
	public List<String[]> read(final MultipartFile file){
		final List<String[]> readData = new ArrayList<String[]>();
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){
			String line;
			while((line = br.readLine()) != null) {
				// ２重ダブルクオーテーションになっていることがある
				String[] data = line.replaceAll("\"", "").split(",", -1);
				if(!isReadTargetData(data[dateIndex])) {continue;}
				readData.add(data);
			}
		}catch(IOException ex) {
			//TODO:例外処理
			ex.printStackTrace();
		}
		return readData;
	}
	
	private boolean isReadTargetData(final String data) {
		return csvReader.isReadTargetData(data);
	}
}
