package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.example.demo.model.BillingStatementList;
import com.example.demo.model.ComparisonResult;
import com.example.demo.model.OrderStatementList;

public class ComparisonResultTests {
	
	@Test
	void outputNotMatchSmbcTest() {
		List<String[]> inputSMBCLine = (List<String[]>) new ArrayList<String[]>(){{
			add(new String[] {"2022/5/13", "dummy1", "1234"});
			add(new String[] {"2022/2/19", "dummy2", "5678"});
		}};
		BillingStatementList billingList = new BillingStatementList(inputSMBCLine);
		List<String[]> inputAmazonLine = (List<String[]>) new ArrayList<String[]>(){{
			add(new String[] {"2022/2/19", "Webカメラ", "999"});
			add(new String[] {"2022/5/13", "昇降チェア\r\nカラーボックス", "1233"});
			add(new String[] {"2022/3/2", "昇降チェア\r\nカラーボックス", "1234"});
		}};
		OrderStatementList orderList = new OrderStatementList(inputAmazonLine);
		
		ComparisonResult result = new ComparisonResult(billingList, orderList);
		result.generateComparisonResult();
		List<Map<String, String>> smbcResult = result.getSmbcNotMatched();
		
		assertThat(smbcResult.get(0).get("billingDate")).isEqualTo("2022/05/13");
		assertThat(smbcResult.get(0).get("biller")).isEqualTo("dummy1");
		assertThat(smbcResult.get(0).get("amount")).isEqualTo("1234");
		assertThat(smbcResult.get(1).get("billingDate")).isEqualTo("2022/02/19");
		assertThat(smbcResult.get(1).get("biller")).isEqualTo("dummy2");
		assertThat(smbcResult.get(1).get("amount")).isEqualTo("5678");
	}
	
	@Test
	void outputNotMatchAmazonTest() {
		List<String[]> inputSMBCLine = (List<String[]>) new ArrayList<String[]>(){{
			add(new String[] {"2022/5/13", "dummy1", "1234"});
			add(new String[] {"2022/2/19", "dummy2", "5678"});
			add(new String[] {"2022/5/15", "dummy3", "999"});
		}};
		BillingStatementList billingList = new BillingStatementList(inputSMBCLine);
		List<String[]> inputAmazonLine = (List<String[]>) new ArrayList<String[]>(){{
			add(new String[] {"2022/2/19", "Webカメラ", "999"});
			add(new String[] {"2022/3/2", "昇降チェア\r\nカラーボックス", "1234"});
		}};
		OrderStatementList orderList = new OrderStatementList(inputAmazonLine);
		
		ComparisonResult result = new ComparisonResult(billingList, orderList);
		result.generateComparisonResult();
		List<Map<String, String>> amazonResult = result.getAmazonNotMatched();
		
		assertThat(amazonResult.get(0).get("billedDate")).isEqualTo("2022/02/19");
		assertThat(amazonResult.get(0).get("itemName")).isEqualTo("Webカメラ");
		assertThat(amazonResult.get(0).get("amount")).isEqualTo("999");
		assertThat(amazonResult.get(1).get("billedDate")).isEqualTo("2022/03/02");
		assertThat(amazonResult.get(1).get("itemName")).isEqualTo("昇降チェア\r\nカラーボックス");
		assertThat(amazonResult.get(1).get("amount")).isEqualTo("1234");
	}
	
	@Test
	void outputMatchTest() {
		List<String[]> inputSMBCLine = (List<String[]>) new ArrayList<String[]>(){{
			add(new String[] {"2022/5/13", "dummy1", "1234"});
			add(new String[] {"2022/3/2", "dummy3", "7321"});
			add(new String[] {"2022/2/19", "dummy2", "999"});
		}};
		BillingStatementList billingList = new BillingStatementList(inputSMBCLine);
		List<String[]> inputAmazonLine = (List<String[]>) new ArrayList<String[]>(){{
			add(new String[] {"2022/3/2", "昇降チェア\r\nカラーボックス", "7321"});
			add(new String[] {"2022/2/19", "Webカメラ", "999"});
		}};
		OrderStatementList orderList = new OrderStatementList(inputAmazonLine);
		
		ComparisonResult result = new ComparisonResult(billingList, orderList);
		result.generateComparisonResult();
		List<Map<String, String>> matchResult = result.getMatched();
		
		assertThat(matchResult.get(0).get("billedDate")).isEqualTo("2022/03/02");
		assertThat(matchResult.get(0).get("itemName")).isEqualTo("昇降チェア\r\nカラーボックス");
		assertThat(matchResult.get(0).get("amount")).isEqualTo("7321");
		assertThat(matchResult.get(1).get("billedDate")).isEqualTo("2022/02/19");
		assertThat(matchResult.get(1).get("itemName")).isEqualTo("Webカメラ");
		assertThat(matchResult.get(1).get("amount")).isEqualTo("999");
		assertThat(matchResult.size()).isEqualTo(2);
	}

}
