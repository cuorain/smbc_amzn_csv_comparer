package com.example.demo.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.AmazonCsvReader;
import com.example.demo.model.BillingStatementList;
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
		final List<String[]> amazonRawData = amazonCsvReader.read(smbcCSV);
		final OrderStatementList amazonData = new OrderStatementList(amazonRawData);
		final List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = new HashMap<String,String>();
		map.put("billingDate", "smbctest");
		map.put("biller", "itemName");
		map.put("amount", "1111");
		list.add(map);
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("billingDate", "smbctest2");
		map2.put("biller", "itemName2");
		map2.put("amount", "1112");
		list.add(map2);
		model.addAttribute("smbcNotMatched", list);
		model.addAttribute("amazonNotMatched", new ArrayList<>());
		model.addAttribute("matched", new ArrayList<>());
		return "topPage";
	}
}
