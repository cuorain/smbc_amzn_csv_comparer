package com.example.demo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SmbcCsvReader extends CsvReader{
	//判定用の日付列
	private static final int dateIndex = 0;
	
	//ユーティリティクラスなのでprivate(Csvを文字列Listで返すだけ）
	private SmbcCsvReader() {
		
	}
	
	public static List<String[]> read(final MultipartFile file){
		final List<String[]> readData = new ArrayList<String[]>();
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), Charset.forName("SJIS")))){
			String line;
			while((line = br.readLine()) != null) {
				final String[] data = line.split(",");
				if(!isReadTargetData(data[dateIndex])) {continue;}
				readData.add(data);
			}
		}catch(IOException ex) {
			//TODO:例外処理
			ex.printStackTrace();
		}
		return readData;
	}
}
