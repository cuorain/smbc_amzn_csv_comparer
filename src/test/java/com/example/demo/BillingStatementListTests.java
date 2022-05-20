package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.model.BillingStatement;
import com.example.demo.model.BillingStatementList;

public class BillingStatementListTests {

	@Test
	void testBuildBillingList() {
		try {
			List<String[]> inputSMBCLine = (List<String[]>) new ArrayList<String[]>(){{
					add(new String[] {"2022/5/13", "dummy1", "1234"});
					add(new String[] {"2022/5/14", "dummy2", "5678"});
					add(new String[] {"2022/5/15", "dummy3", "90"});
			}};
			BillingStatementList billingList = new BillingStatementList(inputSMBCLine);
			//リフレクション
			Field billingListField = BillingStatementList.class.getDeclaredField("billingStatementList");
			billingListField.setAccessible(true);
			List<BillingStatement> billingStatement = (List<BillingStatement>) billingListField.get(billingList);
			Field date = BillingStatement.class.getDeclaredField("billingDate");
			date.setAccessible(true);
			Field amount = BillingStatement.class.getDeclaredField("amount");
			amount.setAccessible(true);
			Field biller = BillingStatement.class.getDeclaredField("biller");
			biller.setAccessible(true);
			
			assertThat((LocalDate)date.get(billingStatement.get(0))).isEqualTo(LocalDate.of(2022, 5, 13));
			assertThat((LocalDate)date.get(billingStatement.get(1))).isEqualTo(LocalDate.of(2022, 5, 14));
			assertThat((LocalDate)date.get(billingStatement.get(2))).isEqualTo(LocalDate.of(2022, 5, 15));
			
			assertThat((BigDecimal)amount.get(billingStatement.get(0))).isEqualTo(new BigDecimal(1234));
			assertThat((BigDecimal)amount.get(billingStatement.get(1))).isEqualTo(new BigDecimal(5678));
			assertThat((BigDecimal)amount.get(billingStatement.get(2))).isEqualTo(new BigDecimal(90));
			
			assertThat((String)biller.get(billingStatement.get(0))).isEqualTo("dummy1");
			assertThat((String)biller.get(billingStatement.get(1))).isEqualTo("dummy2");
			assertThat((String)biller.get(billingStatement.get(2))).isEqualTo("dummy3");
		}catch(NoSuchFieldException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
