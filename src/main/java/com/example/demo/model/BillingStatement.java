package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BillingStatement {
	private final LocalDate billingDate;
	private final String biller;
	private final BigDecimal amount;
	
	public BillingStatement(final String[] line){
		String[] inputDate = line[0].split("/");
		int[] inputDateToInt = Stream.of(inputDate).mapToInt(Integer::parseInt).toArray();
		this.billingDate = LocalDate.of(inputDateToInt[0], inputDateToInt[1], inputDateToInt[2]);
		this.biller = line[1];
		this.amount = new BigDecimal(line[2]);
	}
	
	public boolean matchOrder(final OrderStatement order) {
		return this.getBillingDate().equals(order.getBilledDate())		//請求日比較
			&& this.getAmount().toString().equals(order.getAmount());	//金額比較
	}

	protected String getBillingDate() {
		return this.billingDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}
	
	protected String getAmount() {
		return this.amount.toString();
	}
	
	public static List<Map<String, String>> convertToMapList(final List<BillingStatement> list){
		return list.stream().map(b -> b.asBillingMap()).collect(Collectors.toList());
	}

	public Map<String, String> asBillingMap(){
		Map<String, String> map = new HashMap<String,String>();
		map.put("billingDate", getBillingDate());
		map.put("biller", this.biller);
		map.put("amount", getAmount());
		return map;
	}
}
