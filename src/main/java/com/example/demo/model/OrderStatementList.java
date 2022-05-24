package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderStatementList {
	private final List<OrderStatement> orderStatementList;
	
	// 後で追加できるようにするときは、addとasList(不変化)を作る
	// 今回はリストのルールがない単純なものなので不要→１リスト何件までとかの制約がある時に効果的
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
