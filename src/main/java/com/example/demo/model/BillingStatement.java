package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class BillingStatement {
	private final LocalDate billingDate;
	private final BigDecimal amount;
	
	public BillingStatement(final String[] line){
		String[] inputDate = line[0].split("/");
		int[] inputDateToInt = Stream.of(inputDate).mapToInt(Integer::parseInt).toArray();
		this.billingDate = LocalDate.of(inputDateToInt[0], inputDateToInt[1], inputDateToInt[2]);
		this.amount = new BigDecimal(line[2]);
	}
}
