package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BillingStatementList {
	
	private final List<BillingStatement> billingStatementList;
	
	public BillingStatementList(final List<String[]> lines){
		final List<BillingStatement> list = new ArrayList<BillingStatement>();
		for(final String[] line : lines) {
			BillingStatement inputBilling = new BillingStatement(line);
			list.add(inputBilling);
		}
		this.billingStatementList = Collections.unmodifiableList(list);
	}
	
	protected List<BillingStatement> asList(){
		return this.billingStatementList;
	}
}
