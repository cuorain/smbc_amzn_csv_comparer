package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Amazonからの請求を表す値オブジェクトクラス
 * 
 * @author COSMOROOT
 *
 */
public class OrderStatement {
	/**
	 * 請求確定日
	 */
	private final LocalDate billedDate;
	/**
	 * 金額
	 */
	private final BigDecimal amount;
	/**
	 * 商品名
	 */
	private final String itemName;
	
	/**
	 * コンストラクタ
	 * @param line 注文データ
	 */
	public OrderStatement(final String[] line){
		String[] inputDate = line[0].split("/");
		int[] inputDateToInt = Stream.of(inputDate).mapToInt(Integer::parseInt).toArray();
		this.billedDate = LocalDate.of(inputDateToInt[0], inputDateToInt[1], inputDateToInt[2]);
		this.amount = new BigDecimal(line[2]);
		this.itemName = line[1];
	}
	
	/**
	 * 請求データと照合して対応する請求かを判定する
	 * @param billing 注文
	 * @return 請求に対応するならTrue、それ以外はFalse
	 */
	protected boolean matchBilling(final BillingStatement billing) {
		return this.getBilledDate().equals(billing.getBillingDate())		//請求日比較
			&& this.getAmount().toString().equals(billing.getAmount());	//金額比較
	}
	
	/**
	 * 請求確定日の取得
	 * @return 請求確定日
	 */
	protected String getBilledDate() {
		return this.billedDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}
	
	/**
	 * 商品名の取得
	 * @return 商品名
	 */
	protected String getItemName() {
		return this.itemName;
	}
	
	/**
	 * 金額の取得
	 * @return 金額
	 */
	protected String getAmount() {
		return this.amount.toString();
	}
	
	/**
	 * 注文オブジェクトのリストをマップにした注文リストとして返す
	 * @param list 注文オブジェクトのリスト
	 * @return マップ化された注文のリスト
	 */
	public static List<Map<String, String>> convertToMapList(final List<OrderStatement> list){
		return list.stream().map(b -> b.asOrderMap()).collect(Collectors.toList());
	}
	
	/**
	 * 注文をマップとして返す
	 * @return 注文のMap
	 */
	private Map<String, String> asOrderMap(){
		Map<String, String> map = new HashMap<String,String>();
		map.put("billedDate", getBilledDate());
		map.put("itemName", this.itemName);
		map.put("amount", getAmount());
		return map;
	}
	
}
