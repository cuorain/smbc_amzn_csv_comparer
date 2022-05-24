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

public class AmazonCsvReader{
	//判定用の日付列
	private static final int dateIndex = 0;
	// コンポジションを使う
	private static final CsvReaderUtil csvReader = new CsvReaderUtil();
	
	public AmazonCsvReader() {
		
	}
	
	public static List<String[]> read(final MultipartFile file){
		final List<String[]> readRawData = new ArrayList<String[]>();
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
		
		final List<String[]> compiledReadData = getConpiledOrder(readRawData);
		return compiledReadData;
	}
	
	private static boolean isReadTargetData(final String data) {
		return csvReader.isReadTargetData(data);
	}

	private static List<String[]> getConpiledOrder(final List<String[]> lines) {
		// 注文番号でグルーピング
		final Map<String, List<String[]>> groupedLines = lines.stream().collect(Collectors.groupingBy(s -> s[1]));
		
		final List<String[]> list = new ArrayList<String[]>();
		// グループ毎に１行の注文に整形する
		for(Map.Entry<String, List<String[]>> entry : groupedLines.entrySet()){
			String billedDate = null;
			List<String> itemName = new ArrayList<String>();
			String amount= null;
			final List<String[]> order = entry.getValue();
			for(final String[] line : order) {
				final String lineKind = line[2];
				// TODO:あとで正規表現に
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
