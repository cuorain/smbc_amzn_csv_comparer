package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.demo.model.BillingStatement;

public class BillingStatementTests {
	
	@Test
	void testImportSMBC() {
		try {
			String[] inputSMBCLine = {"2022/5/13", "dummy", "1234"};
			BillingStatement inputBilling = new BillingStatement(inputSMBCLine);
			Field date = BillingStatement.class.getDeclaredField("billingDate");
			date.setAccessible(true);
			assertThat((LocalDate)date.get(inputBilling)).isEqualTo(LocalDate.of(2022, 5, 13));
			assertThat((LocalDate)date.get(inputBilling)).isNotEqualTo(LocalDate.of(2022, 5, 12));
			Field amount = BillingStatement.class.getDeclaredField("amount");
			amount.setAccessible(true);
			assertThat((BigDecimal)amount.get(inputBilling)).isEqualTo(new BigDecimal(1234));
			assertThat((BigDecimal)amount.get(inputBilling)).isNotEqualTo(new BigDecimal(1233));
		}catch(NoSuchFieldException ex) {
			fail();
		} catch (IllegalAccessException e) {
			fail();
		}
	}
}
