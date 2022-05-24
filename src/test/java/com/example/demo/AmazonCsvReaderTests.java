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
			assertThat(readData.get(0)).isEqualTo(new String[] {"2022/5/11","達人に学ぶDB設計 徹底指南書 初級者で終わりたくないあなたへ\r\n達人に学ぶ SQL徹底指南書 (CodeZine BOOKS)","0"});
			assertThat(readData.get(1)).isEqualTo(new String[] {"2022/5/9","テスト駆動開発","2429"});
			assertThat(readData.get(2)).isEqualTo(new String[] {"2022/4/30","マウントハーゲン オーガニック フェアトレード カフェインレスインスタントコーヒー100g インスタント","1491"});
			
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
