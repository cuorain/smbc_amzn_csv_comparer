package com.example.demo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 三井住友カードの請求明細CSVファイルを読み取るクラス
 *  
 * @author COSMOROOT
 *
 */
public class CsvReaderSmbc{
	/**
	 * 判定用の日付列
	 */
	private static final int dateIndex = 0;
	
	public CsvReaderSmbc() {
		
	}
	
	/**
	 * CSVを読み取り、請求データを文字列配列にしたリストを返す
	 * @param file 読み取ったCSVファイル
	 * @return 請求データを格納したリスト
	 */
	public static List<String[]> read(final MultipartFile file){
		final List<String[]> readData = new ArrayList<String[]>();
		// データの読み取り
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
	
	/**
	 * 読み取り対象のデータかを判定する。
	 * 本クラスではdataが日付かどうかで判定
	 * @param data 判定される値（本クラスでは、配列の1番目の要素）
	 * @return dataがyyyy/MM/dd形式ならTrue、それ以外はFalse
	 */
	private static boolean isReadTargetData(final String data) {
		return CsvReaderUtil.isReadTargetData(data);
	}
}
