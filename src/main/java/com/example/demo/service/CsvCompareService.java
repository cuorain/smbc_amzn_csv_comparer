package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.AmazonCsvReader;
import com.example.demo.model.BillingStatementList;
import com.example.demo.model.ComparisonResult;
import com.example.demo.model.OrderStatementList;
import com.example.demo.model.SmbcCsvReader;

@Service
public class CsvCompareService {
	public ComparisonResult getComparingResult(MultipartFile smbcCSV, MultipartFile amazonCSV) {
		//SMBCカード請求データを取得
		final List<String[]> smbcRawData = SmbcCsvReader.read(smbcCSV);
		final BillingStatementList smbcData = new BillingStatementList(smbcRawData);
		
		//Amazon注文データを取得
		final List<String[]> amazonRawData = AmazonCsvReader.read(amazonCSV);
		final OrderStatementList amazonData = new OrderStatementList(amazonRawData);

		// データの比較
		final ComparisonResult result = new ComparisonResult(smbcData, amazonData);
		result.generateComparisonResult();
		return result;
	}
}
