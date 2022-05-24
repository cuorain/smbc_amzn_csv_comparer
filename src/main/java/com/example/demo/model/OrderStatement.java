package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderStatement {
	private final LocalDate billedDate;
	private final BigDecimal amount;
	private final String itemName;
	
	public OrderStatement(final String[] line){
		String[] inputDate = line[0].split("/");
		int[] inputDateToInt = Stream.of(inputDate).mapToInt(Integer::parseInt).toArray();
		this.billedDate = LocalDate.of(inputDateToInt[0], inputDateToInt[1], inputDateToInt[2]);
		this.amount = new BigDecimal(line[2]);
		this.itemName = line[1];
	}
	
	protected boolean matchBilling(final BillingStatement billing) {
		return this.getBilledDate().equals(billing.getBillingDate())		//請求日比較
			&& this.getAmount().toString().equals(billing.getAmount());	//金額比較
	}
	
	protected String getBilledDate() {
		return this.billedDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}
	
	protected String getItemName() {
		return this.itemName;
	}
	
	protected String getAmount() {
		return this.amount.toString();
	}
	
	public static List<Map<String, String>> convertToMapList(final List<OrderStatement> list){
		return list.stream().map(b -> b.asOrderMap()).collect(Collectors.toList());
	}
	
	private Map<String, String> asOrderMap(){
		Map<String, String> map = new HashMap<String,String>();
		map.put("billedDate", getBilledDate());
		map.put("itemName", this.itemName);
		map.put("amount", getAmount());
		return map;
	}
	
}
