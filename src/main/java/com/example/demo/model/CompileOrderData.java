package com.example.demo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompileOrderData {

	private final List<String[]> orderData;
	public CompileOrderData(final String[][] lines){
		final List<String[]> orderData = conpileOrder(lines);
		this.orderData = orderData;
	}
	
	private List<String[]> conpileOrder(final String[][] lines) {
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
			String items = String.join("\r\n", itemName);
			list.add(new String[] {billedDate, items, amount});
		}
		
		
		return list;
	}
	
	private Map<String, List<String[]>> groupingOrder(final String[][] lines){
		final List<String[]> linesAsList = new ArrayList<String[]>(Arrays.asList(lines));
		final Map<String, List<String[]>> result = linesAsList.stream().collect(Collectors.groupingBy(s -> s[1]));
		return result;
	}
}
