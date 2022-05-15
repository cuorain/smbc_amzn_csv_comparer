package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.example.demo.model.CompileOrderData;

public class CompileOrderDataTests {
	
	@Test
	void testBuildAmazonOneOrder() {
		try {
			String[][] inputOrderLines = {
						{"2022/2/18", "249-4581037-7642224", "（注文全体）", "", "", "", "", "1782", "", "", "中根 涼平", "1782", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""},
						{"2022/2/18", "249-4581037-7642224", "Webカメラ", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1782", "1", "1782", "", "中根涼平", "2022年2月18日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/product/B07QMKND9M/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"},
						{"2022/2/18", "249-4581037-7642224", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/2/19", "999", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""}
					};
			CompileOrderData inputOrder = new CompileOrderData(inputOrderLines);
			Field orderLine = CompileOrderData.class.getDeclaredField("orderData");
			orderLine.setAccessible(true);
			List<String[]> order = (List<String[]>)orderLine.get(inputOrder);
			String[] expect = {"2022/2/19", "Webカメラ", "999"};
			//assertThat(order.equals(expect)).isEqualTo(true);
			assertThat(Arrays.toString(order.get(0))).isEqualTo(Arrays.toString(expect));
			
			String[] unexpect = {"2022/2/19", "Webカメラunexpect", "999"};
			assertThat(order.get(0).equals(unexpect[0])).isEqualTo(false);
		}catch(NoSuchFieldException ex) {
			fail();
		} catch (IllegalAccessException e) {
			fail();
		}
	}
	
	@Test
	void testBuildAmazonIncludeMultipleOrder() {
		try {
			String[][] inputMultipleOrder = {
					{"2022/3/1", "249-0204032-0068674", "（注文全体）", "", "", "", "", "9380", "", "", "中根 涼平", "7321", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""},
					{"2022/3/1", "249-0204032-0068674", "昇降チェア", "販売： アマゾンジャパン合同会社  コンディション： 新品", "7499", "1", "7499", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B08CV44VBW/ref=ppx_od_dt_b_asin_title_s01?ie=UTF8&psc=1"},
					{"2022/3/1", "249-0204032-0068674", "カラーボックス", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1881", "1", "1881", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B002NA54SS/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"},
					{"2022/3/1", "249-0204032-0068674", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/3/2", "7321", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""},
					{"2022/3/1", "249-0204032-0068674", "（Amazonポイント）", "※（注文全体）請求額に反映", "", "", "", "-2059", "", "", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""}
				};
			CompileOrderData inputOrder = new CompileOrderData(inputMultipleOrder);
			Field orderLine = CompileOrderData.class.getDeclaredField("orderData");
			orderLine.setAccessible(true);
			List<String[]> order = (List<String[]>)orderLine.get(inputOrder);
			String[] expect = {"2022/3/2", "昇降チェア\r\nカラーボックス", "7321"};
			//assertThat(order.equals(expect)).isEqualTo(true);
			assertThat(Arrays.toString(order.get(0))).isEqualTo(Arrays.toString(expect));
			
			String[] unexpect = {"2022/3/2", "昇降チェアカラーボックス", "999"};
			assertThat(order.get(0).equals(unexpect)).isEqualTo(false);
		}catch(NoSuchFieldException ex) {
			fail();
		} catch (IllegalAccessException e) {
			fail();
		}
	}
	
	@Test
	void testGroupingAmazonOrder() {
		try {
			String[][] inputMultipleOrder = {
					{"2022/3/1", "249-0204032-0068674", "（注文全体）", "", "", "", "", "9380", "", "", "中根 涼平", "7321", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""},
					{"2022/3/1", "249-0204032-0068674", "昇降チェア", "販売： アマゾンジャパン合同会社  コンディション： 新品", "7499", "1", "7499", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B08CV44VBW/ref=ppx_od_dt_b_asin_title_s01?ie=UTF8&psc=1"},
					{"2022/3/1", "249-0204032-0068674", "カラーボックス", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1881", "1", "1881", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B002NA54SS/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"},
					{"2022/3/1", "249-0204032-0068674", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/3/2", "7321", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""},
					{"2022/3/1", "249-0204032-0068674", "（Amazonポイント）", "※（注文全体）請求額に反映", "", "", "", "-2059", "", "", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""},
					{"2022/2/18", "249-4581037-7642224", "（注文全体）", "", "", "", "", "1782", "", "", "中根 涼平", "1782", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""},
					{"2022/2/18", "249-4581037-7642224", "Webカメラ", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1782", "1", "1782", "", "中根涼平", "2022年2月18日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/product/B07QMKND9M/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"},
					{"2022/2/18", "249-4581037-7642224", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/2/19", "999", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""}
				};
			CompileOrderData inputOrder = new CompileOrderData(inputMultipleOrder);
//			ConpileOrderData inputOrder = ConpileOrderData.class.getDeclaredConstructor().newInstance(inputMultipleOrder);
			Method testMethod = CompileOrderData.class.getDeclaredMethod("groupingOrder", String[][].class);
			testMethod.setAccessible(true);
			Object[] param = {inputMultipleOrder};
			Map<String, List<String[]>> groupedOrder = (Map<String, List<String[]>>) testMethod.invoke(inputOrder, param);
			List<String[]> order_1 = groupedOrder.get("249-0204032-0068674");
			List<String[]> expect_1 = new ArrayList<String[]>(){{
					add(new String[] {"2022/3/1", "249-0204032-0068674", "（注文全体）", "", "", "", "", "9380", "", "", "中根 涼平", "7321", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""});
					add(new String[] {"2022/3/1", "249-0204032-0068674", "昇降チェア", "販売： アマゾンジャパン合同会社  コンディション： 新品", "7499", "1", "7499", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B08CV44VBW/ref=ppx_od_dt_b_asin_title_s01?ie=UTF8&psc=1"});
					add(new String[] {"2022/3/1", "249-0204032-0068674", "カラーボックス", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1881", "1", "1881", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B002NA54SS/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"});
					add(new String[] {"2022/3/1", "249-0204032-0068674", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/3/2", "7321", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""});
					add(new String[] {"2022/3/1", "249-0204032-0068674", "（Amazonポイント）", "※（注文全体）請求額に反映", "", "", "", "-2059", "", "", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""});
			}};
			assertThat(order_1.get(0)).isEqualTo(expect_1.get(0));
			assertThat(order_1.get(1)).isEqualTo(expect_1.get(1));
			assertThat(order_1.get(2)).isEqualTo(expect_1.get(2));
			assertThat(order_1.get(3)).isEqualTo(expect_1.get(3));
			assertThat(order_1.get(4)).isEqualTo(expect_1.get(4));
			
			List<String[]> order_2 = groupedOrder.get("249-4581037-7642224");
			List<String[]> expect_2 = new ArrayList<String[]>(){{
					add(new String[] {"2022/2/18", "249-4581037-7642224", "（注文全体）", "", "", "", "", "1782", "", "", "中根 涼平", "1782", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""});
					add(new String[] {"2022/2/18", "249-4581037-7642224", "Webカメラ", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1782", "1", "1782", "", "中根涼平", "2022年2月18日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/product/B07QMKND9M/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"});
					add(new String[] {"2022/2/18", "249-4581037-7642224", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/2/19", "999", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""});
			}};
			assertThat(order_2.get(0)).isEqualTo(expect_2.get(0));
			assertThat(order_2.get(1)).isEqualTo(expect_2.get(1));
			assertThat(order_2.get(2)).isEqualTo(expect_2.get(2));
		} catch (IllegalAccessException e) {
			fail("IllegalAccessException");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("IllegalArgumentException");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail(e.getTargetException());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (SecurityException e) {
			fail("SecurityException");
		}
	}
	
	@Test
	void testCompileAmazonOrder() {
		try {
			String[][] inputMultipleOrder = {
					{"2022/2/18", "249-4581037-7642224", "（注文全体）", "", "", "", "", "1782", "", "", "中根 涼平", "1782", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""},
					{"2022/2/18", "249-4581037-7642224", "Webカメラ", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1782", "1", "1782", "", "中根涼平", "2022年2月18日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/product/B07QMKND9M/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"},
					{"2022/2/18", "249-4581037-7642224", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/2/19", "999", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-4581037-7642224", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o03?ie=UTF8&orderID=249-4581037-7642224", ""},
					{"2022/3/1", "249-0204032-0068674", "（注文全体）", "", "", "", "", "9380", "", "", "中根 涼平", "7321", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""},
					{"2022/3/1", "249-0204032-0068674", "昇降チェア", "販売： アマゾンジャパン合同会社  コンディション： 新品", "7499", "1", "7499", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B08CV44VBW/ref=ppx_od_dt_b_asin_title_s01?ie=UTF8&psc=1"},
					{"2022/3/1", "249-0204032-0068674", "カラーボックス", "販売： アマゾンジャパン合同会社  コンディション： 新品", "1881", "1", "1881", "", "中根 涼平", "2022年3月2日に発送済み", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/product/B002NA54SS/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"},
					{"2022/3/1", "249-0204032-0068674", "（クレジットカードへの請求）", "", "", "", "", "", "", "", "中根 涼平", "", "2022/3/2", "7321", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""},
					{"2022/3/1", "249-0204032-0068674", "（Amazonポイント）", "※（注文全体）請求額に反映", "", "", "", "-2059", "", "", "中根 涼平", "", "", "", "MasterCard（下4けたが3302）", "https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0204032-0068674", "https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o07?ie=UTF8&orderID=249-0204032-0068674", ""}
				};
			CompileOrderData inputOrder = new CompileOrderData(inputMultipleOrder);
			Field orderLine = CompileOrderData.class.getDeclaredField("orderData");
			orderLine.setAccessible(true);
			List<String[]> order = (List<String[]>)orderLine.get(inputOrder);
			String[][] expect = {
					{"2022/2/19", "Webカメラ", "999"},
					{"2022/3/2", "昇降チェア\r\nカラーボックス", "7321"}
				};
			assertThat(Arrays.toString(order.get(0))).isEqualTo(Arrays.toString(expect[0]));
			assertThat(Arrays.toString(order.get(1))).isEqualTo(Arrays.toString(expect[1]));
		}catch(NoSuchFieldException ex) {
			fail();
		} catch (IllegalAccessException e) {
			fail();
		}
	}
}
