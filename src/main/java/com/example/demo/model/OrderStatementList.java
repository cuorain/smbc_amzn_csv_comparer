package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderStatementList {
	private final List<OrderStatement> orderStatementList;
	
	// 後で追加できるようにするときは、addとasList(不変化)を作る
	public OrderStatementList(final List<String[]> lines){
		final List<OrderStatement> list = new ArrayList<OrderStatement>();
		for(final String[] line : lines) {
			OrderStatement inputOrder = new OrderStatement(line);
			list.add(inputOrder);
		}
		this.orderStatementList = Collections.unmodifiableList(list);
	}
	
	public List<OrderStatement> asList(){
		return this.orderStatementList;
	}
	
}
