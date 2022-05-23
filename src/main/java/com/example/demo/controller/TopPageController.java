package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.AmazonCsvReader;
import com.example.demo.model.BillingStatementList;
import com.example.demo.model.ComparisonResult;
import com.example.demo.model.CompileOrderData;
import com.example.demo.model.OrderStatementList;
import com.example.demo.model.SmbcCsvReader;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TopPageController {
	@GetMapping("/")
	public String topPage(Model model) {
		return "topPage";
	}
	
	@PostMapping(value = "/", params = "import_file")
	public String runCheckCSVDifference(@RequestParam("smbc_import_file") MultipartFile smbcCSV, @RequestParam("amazon_import_file") MultipartFile amazonCSV, Model model) {
		//SMBCカード請求データを取得
		final SmbcCsvReader smbcCsvReader = new SmbcCsvReader();
		final List<String[]> smbcRawData = smbcCsvReader.read(smbcCSV);
		final BillingStatementList smbcData = new BillingStatementList(smbcRawData);
		
		//Amazon注文データを取得
		final AmazonCsvReader amazonCsvReader = new AmazonCsvReader();
		final List<String[]> amazonRawData = amazonCsvReader.read(amazonCSV);
		final List<String[]> amazonCompiledData = CompileOrderData.getConpiledOrder(amazonRawData);
		final OrderStatementList amazonData = new OrderStatementList(amazonCompiledData);

		// データの比較
		final ComparisonResult result = new ComparisonResult(smbcData, amazonData);
		result.generateComparisonResult();
		model.addAttribute("smbcNotMatched", result.getSmbcNotMatched());
		model.addAttribute("amazonNotMatched", result.getAmazonNotMatched());
		model.addAttribute("matched", result.getMatched());
		return "topPage";
	}
}
