package com.example.demo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;


/**
 * Amazonの注文明細CSVファイルを読み取るクラス
 *  
 * @author COSMOROOT
 *
 */
public class AmazonCsvReader{
	/**
	 * 判定用の日付列
	 */
	private static final int dateIndex = 0;
	/**
	 * CsvReaderUtil(共通処理用)
	 */
	// コンポジションを使う
	private static final CsvReaderUtil csvReader = new CsvReaderUtil();
	
	private AmazonCsvReader() {
		
	}
	
	/**
	 * CSVを読み取り、注文データを文字列配列にしたリストを返す
	 * @param file 読み取ったCSVファイル
	 * @return 注文データを格納したリスト
	 */
	public static List<String[]> read(final MultipartFile file){
		final List<String[]> readRawData = new ArrayList<String[]>();
		// データの読み取り
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){
			String line;
			while((line = br.readLine()) != null) {
				// ２重ダブルクオーテーションになっていることがある
				String[] data = line.replaceAll("\"", "").split(",", -1);
				
				// 対象となるデータだけリストに格納する
				if(!isReadTargetData(data[dateIndex])) {continue;}
				readRawData.add(data);
			}
		}catch(IOException ex) {
			//TODO:例外処理
			ex.printStackTrace();
		}
		
		final List<String[]> compiledReadData = getCompiledOrder(readRawData);
		return compiledReadData;
	}
	
	/**
	 * 読み取り対象のデータかを判定する。
	 * 本クラスではdataが日付かどうかで判定
	 * @param data 判定される値（本クラスでは、配列の1番目の要素）
	 * @return dataがyyyy/MM/dd形式ならTrue、それ以外はFalse
	 */
	private static boolean isReadTargetData(final String data) {
		return csvReader.isReadTargetData(data);
	}

	
	/**
	 * CSVから読み込んだままの、1注文複数行になっている注文リストを、1行1注文に整形する。
	 * @param lines 読み取ったCSVデータ
	 * @return 注文番号毎に1行にしたリスト
	 */
	private static List<String[]> getCompiledOrder(final List<String[]> lines) {
		// 注文番号でグルーピング
		final Map<String, List<String[]>> groupedLines = lines.stream().collect(Collectors.groupingBy(s -> s[1]));
		
		final List<String[]> list = new ArrayList<String[]>();
		// グループ毎に１行の注文に整形する
		for(Map.Entry<String, List<String[]>> entry : groupedLines.entrySet()){
			String billedDate = null;
			List<String> itemName = new ArrayList<String>();
			String amount= null;
			final List<String[]> order = entry.getValue();
			
			// 複数行から必要な情報だけ取得する
			for(final String[] line : order) {
				final String lineKind = line[2];
				// TODO:あとで正規表現に
				// Amazonの注文明細は、商品名列が、（）で囲われていないやつが商品名になっている
				if(!lineKind.startsWith("（") && !lineKind.endsWith("）")) {
					itemName.add(lineKind);
					continue;
				// 請求日と請求額は１度だけ代入される。
				}else if(lineKind.equals("（クレジットカードへの請求）") && Objects.equals(billedDate, null) && Objects.equals(amount, null)){
					billedDate = line[12];
					amount = line[13];
				}
			}
			
			// 商品名を改行コード区切りで出力
			final String items = String.join("\r\n", itemName);
			// ポイント支払等、請求額が０の場合
			if(billedDate == null || amount == null) {
				// 事前に0番目に日付が入る行を対象にしているのでどの行でも日付は取れるはず
				// FIXME: 保守性がとても悪いコード（行位置、列位置がべた書き）
				list.add(new String[] {order.get(0)[0], items, "0"});
			}else {
				list.add(new String[] {billedDate, items, amount});
			}
			
		}
		
		
		return list;
	}
}
