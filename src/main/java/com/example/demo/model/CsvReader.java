package com.example.demo.model;

import java.text.SimpleDateFormat;

public class CsvReader {
	
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
