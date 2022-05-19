package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.io.FileInputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.example.demo.model.AmazonCsvReader;

public class AmazonCsvReaderTests {

	@Test
	void testImportAmazon() {
		try {
			String fileName = AmazonCsvReaderTests.class.getClassLoader().getResource("Amazon_Test.csv").getPath();
			FileInputStream inputFile = new FileInputStream(fileName);
			MockMultipartFile file = new MockMultipartFile("testFile", inputFile);
			final AmazonCsvReader reader = new AmazonCsvReader();
			List<String[]> readData = reader.read(file);
			assertThat(readData.get(0)).isEqualTo(new String[] {"2022/4/30","249-0812423-5520631","（注文全体）","","","","","1491","","","中根 涼平","1491","","","MasterCard（下4けたが3302）","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0812423-5520631","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o02?ie=UTF8&orderID=249-0812423-5520631",""});
			assertThat(readData.get(1)).isEqualTo(new String[] {"2022/4/30","249-0812423-5520631","マウントハーゲン オーガニック フェアトレード カフェインレスインスタントコーヒー100g インスタント","販売： アマゾンジャパン合同会社  コンディション： 新品","1491","1","1491","","中根 涼平","2022年4月30日に発送済み","中根 涼平","","","","MasterCard（下4けたが3302）","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0812423-5520631","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o02?ie=UTF8&orderID=249-0812423-5520631","https://www.amazon.co.jp/gp/product/B01C5JMW90/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"});
			assertThat(readData.get(2)).isEqualTo(new String[] {"2022/4/30","249-0812423-5520631","（クレジットカードへの請求）","","","","","","","","中根 涼平","","2022/4/30","1491","MasterCard（下4けたが3302）","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-0812423-5520631","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o02?ie=UTF8&orderID=249-0812423-5520631",""});
			assertThat(readData.get(3)).isEqualTo(new String[] {"2022/5/8","249-3482868-9039846","（注文全体）","","","","","2429","","","中根 涼平","2429","","","MasterCard（下4けたが3302）","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-3482868-9039846","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o01?ie=UTF8&orderID=249-3482868-9039846",""});
			assertThat(readData.get(4)).isEqualTo(new String[] {"2022/5/8","249-3482868-9039846","テスト駆動開発","Kent Beck 販売： ブックスドリーム 参考書・専門書・医学書専門店【30日返品保証】【お急ぎ便・お届け日時指定便対応可】   コンディション： 中古品 - 非常に良い 【通常配送送料無料】【30日間返品保証有り】【お急ぎ便＆お届け日時指定便対応可】書き込みありません。古本ではございますが、使用感少なくきれいな状態の書籍です。弊社基準で良よりコンデションが良いと判断された商品となります。水濡れ防止梱包の上、迅速丁寧に発送させていただきます。【30日間返品保証：商品状態に当商品コメントと相違する点があった場合は到着後30日間、返品・返金を承ります】《通常配送の配送料は無料です》【お届け日時指定便は、最短でご注文日の翌日以降からご都合の良い到着日時を指定してご注文頂けます】《マケプレお急ぎ便は、速達扱いの配送方法で、発送翌日（北海道・沖縄県は発送翌々日）に到着するように発送し、最短でご注文日の翌日に到着します》【お届け日時指定便とマケプレお急ぎ便は、時期による運送会社の物量、天候状況、配送地域等によってはご利用頂けない場合がございます（注文画面で選択可能であればご利用可能です）》","2429","1","2429","","中根 涼平","2022年5月9日に発送済み","中根 涼平","","","","MasterCard（下4けたが3302）","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-3482868-9039846","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o01?ie=UTF8&orderID=249-3482868-9039846","https://www.amazon.co.jp/gp/product/4274217884/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"});
			assertThat(readData.get(5)).isEqualTo(new String[] {"2022/5/8","249-3482868-9039846","（クレジットカードへの請求）","","","","","","","","中根 涼平","","2022/5/9","2429","MasterCard（下4けたが3302）","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-3482868-9039846","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o01?ie=UTF8&orderID=249-3482868-9039846",""});
			assertThat(readData.get(6)).isEqualTo(new String[] {"2022/5/11","249-2238901-1439825","（注文全体）","","","","","2115","","","中根 涼平","79","","","","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-2238901-1439825","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o00?ie=UTF8&orderID=249-2238901-1439825",""});
			assertThat(readData.get(7)).isEqualTo(new String[] {"2022/5/11","249-2238901-1439825","達人に学ぶDB設計 徹底指南書 初級者で終わりたくないあなたへ","ミック 販売： バリューブックス 【防水梱包で、丁寧に発送します】   コンディション： 中古品 - 可 ◆◆◆カバーなし。迅速・丁寧な発送を心がけております。【毎日発送】","1537","1","1615","","中根 涼平","出荷準備中","中根 涼平","","","","","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-2238901-1439825","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o00?ie=UTF8&orderID=249-2238901-1439825","https://www.amazon.co.jp/gp/product/4798124702/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"});
			assertThat(readData.get(8)).isEqualTo(new String[] {"2022/5/11","249-2238901-1439825","達人に学ぶ SQL徹底指南書 (CodeZine BOOKS)","ミック 販売： バリューブックス 【防水梱包で、丁寧に発送します】   コンディション： 中古品 - 良い ◆◆◆おおむね良好な状態です。中古商品のため若干のスレ、日焼け、使用感等ある場合がございますが、品質には十分注意して発送いたします。 【毎日発送】","78","1","","","中根 涼平","出荷準備中","中根 涼平","","","","","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-2238901-1439825","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o00?ie=UTF8&orderID=249-2238901-1439825","https://www.amazon.co.jp/gp/product/4798115169/ref=ppx_od_dt_b_asin_title_s00?ie=UTF8&psc=1"});
			assertThat(readData.get(9)).isEqualTo(new String[] {"2022/5/11","249-2238901-1439825","（配送料・手数料）","※（注文全体）注文合計に反映","","","500","","","","中根 涼平","","","","","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-2238901-1439825","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o00?ie=UTF8&orderID=249-2238901-1439825",""});
			assertThat(readData.get(10)).isEqualTo(new String[] {"2022/5/11","249-2238901-1439825","（Amazonポイント）","※（注文全体）請求額に反映","","","","-2036","","","中根 涼平","","","","","https://www.amazon.co.jp/gp/css/summary/edit.html?ie=UTF8&orderID=249-2238901-1439825","https://www.amazon.co.jp/gp/css/summary/print.html/ref=ppx_yo_dt_b_invoice_o00?ie=UTF8&orderID=249-2238901-1439825",""});
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
