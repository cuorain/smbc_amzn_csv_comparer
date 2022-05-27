package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.BillingStatementList;
import com.example.demo.model.ComparisonResult;
import com.example.demo.model.CsvReaderAmazon;
import com.example.demo.model.CsvReaderSmbc;
import com.example.demo.model.OrderStatementList;

@Service
public class SmbcAmazonCsvCompareService {
	public ComparisonResult getCompareResult(MultipartFile smbcCSV, MultipartFile amazonCSV) {
		//SMBCカード請求データを取得
		final List<String[]> smbcRawData = CsvReaderSmbc.read(smbcCSV);
		final BillingStatementList smbcData = new BillingStatementList(smbcRawData);
		
		//Amazon注文データを取得
		final List<String[]> amazonRawData = CsvReaderAmazon.read(amazonCSV);
		final OrderStatementList amazonData = new OrderStatementList(amazonRawData);

		// データの比較
		final ComparisonResult result = new ComparisonResult(smbcData, amazonData);
		result.generateComparisonResult();
		return result;
	}
}
