package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.demo.model.OrderStatement;

public class OrderStatementTests {

	@Test
	void testImportAmazon() {
		try {
			String[] inputAmazonLine = {"2022/5/13", "テスト駆動開発入門書", "1782"};
			OrderStatement inputOrder = new OrderStatement(inputAmazonLine);
			
			Field date = OrderStatement.class.getDeclaredField("billedDate");
			date.setAccessible(true);
			assertThat((LocalDate)date.get(inputOrder)).isEqualTo(LocalDate.of(2022, 5, 13));
			assertThat((LocalDate)date.get(inputOrder)).isNotEqualTo(LocalDate.of(2022, 5, 12));
			
			Field item = OrderStatement.class.getDeclaredField("itemName");
			item.setAccessible(true);
			assertThat((String)item.get(inputOrder)).isEqualTo("テスト駆動開発入門書");
			assertThat((String)item.get(inputOrder)).isNotEqualTo("Test駆動開発入門書");
			
			Field amount = OrderStatement.class.getDeclaredField("amount");
			amount.setAccessible(true);
			assertThat((BigDecimal)amount.get(inputOrder)).isEqualTo(new BigDecimal(1782));
			assertThat((BigDecimal)amount.get(inputOrder)).isNotEqualTo(new BigDecimal(1712));
			
		}catch(NoSuchFieldException ex) {
			fail();
		} catch (IllegalAccessException e) {
			fail();
		}
	}
	
	
}
