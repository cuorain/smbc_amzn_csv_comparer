package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 注文と請求の照合結果を持つクラス
 * 
 * @author COSMOROOT
 *
 */
public class ComparisonResult {
	/**
	 * 請求リスト
	 */
	private final List<BillingStatement> billingList;
	/**
	 * 注文リスト
	 */
	private final List<OrderStatement> orderList;
	/**
	 * 一致しなかった請求のリスト
	 */
	private List<Map<String, String>> smbcNotMatched = null;
	/**
	 * 一致しなかった注文のリスト
	 */
	private List<Map<String, String>> amazonNotMatched = null;
	/**
	 * 一致した請求（注文）のリスト
	 */
	private List<Map<String, String>> matched = null;
	
	/**
	 * コンストラクタ
	 * @param billingList 請求リストオブジェクト
	 * @param orderList 注文リストオブジェクト
	 */
	public ComparisonResult(final BillingStatementList billingList, final OrderStatementList orderList){
		this.billingList = billingList.asList();
		this.orderList = orderList.asList();
	}
	
	/**
	 * 照合結果の生成
	 */
	public void generateComparisonResult() {
		final List<OrderStatement> matchedList = new ArrayList<OrderStatement>();
		final List<BillingStatement> notMatchedBillingList = new ArrayList<BillingStatement>();
		final List<OrderStatement> notMatchedOrderList = new ArrayList<OrderStatement>();
		// 請求リストの照合
		for(final BillingStatement billing : billingList) {
			if(!orderList.stream().anyMatch(order -> billing.matchOrder(order))) {
				notMatchedBillingList.add(billing);
			}
		}
		this.smbcNotMatched = BillingStatement.convertToMapList(notMatchedBillingList);
		
		//注文リストの照合
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
	
	/**
	 * 
	 * @return 一致しなかった請求のリスト
	 */
	public List<Map<String, String>> getSmbcNotMatched(){
		return this.smbcNotMatched;
	}
	
	/**
	 * 
	 * @return 一致しなかった注文のリスト
	 */
	public List<Map<String, String>> getAmazonNotMatched(){
		return this.amazonNotMatched;
	}
	
	/**
	 * 
	 * @return 一致した請求（注文）のリスト
	 */
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
