package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
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
}
