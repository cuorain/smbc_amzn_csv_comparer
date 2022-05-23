package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComparisonResult {
	private final List<BillingStatement> billingList;
	private final List<OrderStatement> orderList;
	private List<Map<String, String>> smbcNotMatched = null;
	private List<Map<String, String>> amazonNotMatched = null;
	private List<Map<String, String>> matched = null;
	
	public ComparisonResult(final BillingStatementList billingList, final OrderStatementList orderList){
		this.billingList = billingList.asList();
		this.orderList = orderList.asList();
	}
	
	public void generateComparisonResult() {
		final List<OrderStatement> matchedList = new ArrayList<OrderStatement>();
		final List<BillingStatement> notMatchedBillingList = new ArrayList<BillingStatement>();
		final List<OrderStatement> notMatchedOrderList = new ArrayList<OrderStatement>();
		for(final BillingStatement billing : billingList) {
			if(!orderList.stream().anyMatch(order -> billing.matchOrder(order))) {
				notMatchedBillingList.add(billing);
			}
		}
		this.smbcNotMatched = BillingStatement.convertToMapList(notMatchedBillingList);
		
		//HACK:２回ループするのは非効率なのでなんとかしたい。
		for(final OrderStatement order : orderList) {
			if(billingList.stream().anyMatch(billing -> order.matchBilling(billing))) {
				matchedList.add(order);
			}else{
				notMatchedOrderList.add(order);
			}
		}
		this.amazonNotMatched = OrderStatement.convertToMapList(notMatchedOrderList);
		this.matched = OrderStatement.convertToMapList(matchedList);
	}
	
	public List<Map<String, String>> getSmbcNotMatched(){
		return this.smbcNotMatched;
	}
	
	public List<Map<String, String>> getAmazonNotMatched(){
		return this.amazonNotMatched;
	}
	
	public List<Map<String, String>> getMatched(){
		return this.matched;
	}
	
	// 参考で残しておく。finalなメンバ変数を諦めない方法。（複雑になる）
//	public ComparisonResult(final BillingStatementList billingList, final OrderStatementList orderList){
//		this(initialize(billingList, orderList));
//	}
//	
//	private ComparisonResult(final InitializationParam param) {
//		this.smbcNotMatched = param.smbcNotMatched;
//	}
//	
//	private static InitializationParam initialize(final BillingStatementList billingList, final OrderStatementList orderList) {
//		InitializationParam result = new InitializationParam(billingList, orderList);
//		return result;
//	}
//	
//	public static List<Map<String, String>> getSmbcNotMatched(){
//		return 
//	}
//	
//	// 初期化専用クラス
//	private static final class InitializationParam {
//		private List<Map<String, String>> smbcNotMatched;
//		private List<Map<String, String>> amazonNotMatched;
//		private List<Map<String, String>> matched;
//		
//		InitializationParam(final BillingStatementList billingList, final OrderStatementList orderList){
//			this.compare(billingList.asList(), orderList.asList());
//		}
//
//		private void compare(final List<BillingStatement> billingList, final List<OrderStatement>orderList){
//			final List<BillingStatement> matchedList = new ArrayList<BillingStatement>();
//			final List<BillingStatement> notMatchedBillingList = new ArrayList<BillingStatement>();
//			for(final BillingStatement billing : billingList) {
//				if(orderList.stream().anyMatch(order -> billing.matchOrder(order))) {
//					matchedList.add(billing);
//				}else {
//					notMatchedBillingList.add(billing);
//				}
//			}
//			this.smbcNotMatched = convertToMapList(notMatchedBillingList);
//		}
//
//		private List<Map<String, String>> convertToMapList(final List<BillingStatement> list){
//			return list.stream().map(b -> b.asMap()).collect(Collectors.toList());
//		}
//	}
}
