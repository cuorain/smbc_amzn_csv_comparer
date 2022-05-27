package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.io.FileInputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.example.demo.model.CsvReaderSmbc;

public class SmbcCsvReaderTests {
	@Test
	void readCSV() {
		try {
			String fileName = SmbcCsvReaderTests.class.getClassLoader().getResource("SMBC_Test.csv").getPath();
			FileInputStream inputFile = new FileInputStream(fileName);
			MockMultipartFile file = new MockMultipartFile("testFile", inputFile);
			List<String[]> readData = CsvReaderSmbc.read(file);
			assertThat(readData.get(0)).isEqualTo(new String[] {"2022/04/09", "ＡＭＡＺＯＮ．ＣＯ．ＪＰ", "1345"});
			assertThat(readData.get(1)).isEqualTo(new String[] {"2022/04/09", "ＡＭＡＺＯＮ．ＣＯ．ＪＰ", "2266"});
			assertThat(readData.get(2)).isEqualTo(new String[] {"2022/04/12", "ＡＭＡＺＯＮ．ＣＯ．ＪＰ", "24461"});
			assertThat(readData.get(3)).isEqualTo(new String[] {"2022/04/20", "ＧＯＯＧＬＥ　＊Ｇｏｏｇｌｅ　Ｓｔｏｒａ", "250"});
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
