package com.example.demo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CsvReaderUtil {
	
	//読み込む行かどうかを判定
	protected boolean isReadTargetData(final String data) {
		try{
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		    // 厳密にチェック
		    sdf.setLenient(false);
		    sdf.parse(data);
		    return true;
		 
		  }catch(Exception ex){
		    return false;
		  }
	}
}
