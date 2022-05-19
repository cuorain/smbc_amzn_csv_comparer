package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.model.OrderStatement;
import com.example.demo.model.OrderStatementList;

public class OrderStatementListTests {

	@Test
	void testBuildOrderList() {
		try {
			List<String[]> inputAmazonLine = (List<String[]>) new ArrayList<String[]>(){{
				add(new String[] {"2022/2/19", "Webカメラ", "999"});
				add(new String[] {"2022/3/2", "昇降チェア\r\nカラーボックス", "7321"});
		}};
			OrderStatementList orderList = new OrderStatementList(inputAmazonLine);
			//リフレクション
			Field orderListField = OrderStatementList.class.getDeclaredField("orderStatementList");
			orderListField.setAccessible(true);
			List<OrderStatement> orderStatement = (List<OrderStatement>) orderListField.get(orderList);
			Field date = OrderStatement.class.getDeclaredField("billedDate");
			date.setAccessible(true);
			Field amount = OrderStatement.class.getDeclaredField("amount");
			amount.setAccessible(true);
			Field itemName = OrderStatement.class.getDeclaredField("itemName");
			itemName.setAccessible(true);
			
			assertThat((LocalDate)date.get(orderStatement.get(0))).isEqualTo(LocalDate.of(2022, 2, 19));
			assertThat((LocalDate)date.get(orderStatement.get(1))).isEqualTo(LocalDate.of(2022, 3, 2));
			
			assertThat((BigDecimal)amount.get(orderStatement.get(0))).isEqualTo(new BigDecimal(999));
			assertThat((BigDecimal)amount.get(orderStatement.get(1))).isEqualTo(new BigDecimal(7321));
			
			assertThat((String)itemName.get(orderStatement.get(0))).isEqualTo("Webカメラ");
			assertThat((String)itemName.get(orderStatement.get(1))).isEqualTo("昇降チェア\r\nカラーボックス");
		}catch(NoSuchFieldException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
