package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompileOrderData {

	public CompileOrderData(){
	}
	
	public static List<String[]> getConpiledOrder(final List<String[]> lines) {
		final Map<String, List<String[]>> groupedLines = groupingOrder(lines);
		final List<String[]> list = new ArrayList<String[]>();
		
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
	
	private static Map<String, List<String[]>> groupingOrder(final List<String[]> lines){
		final Map<String, List<String[]>> result = lines.stream().collect(Collectors.groupingBy(s -> s[1]));
		return result;
	}
}
