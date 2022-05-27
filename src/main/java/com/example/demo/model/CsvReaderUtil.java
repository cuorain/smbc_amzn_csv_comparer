package com.example.demo.model;

import java.text.SimpleDateFormat;

/**
 * Csvの共通する処理を含むクラス
 * 
 * @author COSMOROOT
 *
 */
public class CsvReaderUtil {
	
	/**
	 * 読み込む行かどうかを判定
	 * @param data 判定される値
	 * @return dataがyyyy/MM/dd形式ならTrue、それ以外はFalse
	 */
	protected static boolean isReadTargetData(final String data) {
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
