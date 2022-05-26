package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 三井住友カードからの請求リスト（コレクションオブジェクト）
 */
public class BillingStatementList {
	/**
	 * 請求リスト
	 */
	private final List<BillingStatement> billingStatementList;
	
	/**
	 * コンストラクタ
	 * @param lines
	 */
	public BillingStatementList(final List<String[]> lines){
		final List<BillingStatement> list = new ArrayList<BillingStatement>();
		for(final String[] line : lines) {
			BillingStatement inputBilling = new BillingStatement(line);
			list.add(inputBilling);
		}
		this.billingStatementList = Collections.unmodifiableList(list);
	}
	
	/**
	 * リストとして返す
	 * @return 請求リスト
	 */
	protected List<BillingStatement> asList(){
		return this.billingStatementList;
	}
}
