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
 * 三井住友カードからの請求を表す値オブジェクトクラス
 * 
 * @author COSMOROOT
 *
 */
public class BillingStatement {
	/**
	 * 請求日
	 */
	private final LocalDate billingDate;
	/**
	 * 請求者
	 */
	private final String biller;
	/**
	 * 金額
	 */
	private final BigDecimal amount;
	
	/**
	 * コンストラクタ
	 * 
	 * @param line 請求データ
	 */
	public BillingStatement(final String[] line){
		String[] inputDate = line[0].split("/");
		int[] inputDateToInt = Stream.of(inputDate).mapToInt(Integer::parseInt).toArray();
		this.billingDate = LocalDate.of(inputDateToInt[0], inputDateToInt[1], inputDateToInt[2]);
		this.biller = line[1];
		this.amount = new BigDecimal(line[2]);
	}
	
	/**
	 * 注文データと照合して対応する注文かを判定する
	 * @param order 注文
	 * @return 注文に対応するならTrue、それ以外はFalse
	 */
	protected boolean matchOrder(final OrderStatement order) {
		return this.getBillingDate().equals(order.getBilledDate())		//請求日比較
			&& this.getAmount().toString().equals(order.getAmount());	//金額比較
	}

	/**
	 * 請求日の取得
	 * @return 請求日
	 */
	protected String getBillingDate() {
		return this.billingDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}
	
	/**
	 * 金額の取得
	 * @return 金額
	 */
	protected String getAmount() {
		return this.amount.toString();
	}
	
	/**
	 * 請求オブジェクトのリストをマップにした請求リストとして返す
	 * @param list 請求オブジェクトのリスト
	 * @return マップ化された請求のリスト
	 */
	protected static List<Map<String, String>> convertToMapList(final List<BillingStatement> list){
		return list.stream().map(b -> b.asBillingMap()).collect(Collectors.toList());
	}

	/**
	 * 請求をマップとして返す
	 * @return 請求のMap
	 */
	private Map<String, String> asBillingMap(){
		Map<String, String> map = new HashMap<String,String>();
		map.put("billingDate", getBillingDate());
		map.put("biller", this.biller);
		map.put("amount", getAmount());
		return map;
	}
}
