package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.ComparisonResult;
import com.example.demo.service.CsvCompareService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TopPageController {
	@Autowired
	CsvCompareService csvCompareService;
	
	@GetMapping("/")
	public String topPage(Model model) {
		return "topPage";
	}
	
	@PostMapping(value = "/", params = "import_file")
	public String showComparingResult(@RequestParam("smbc_import_file") MultipartFile smbcCSV, @RequestParam("amazon_import_file") MultipartFile amazonCSV, Model model) {
		final ComparisonResult result = csvCompareService.getComparingResult(smbcCSV, amazonCSV);
		model.addAttribute("smbcNotMatched", result.getSmbcNotMatched());
		model.addAttribute("amazonNotMatched", result.getAmazonNotMatched());
		model.addAttribute("matched", result.getMatched());
		return "topPage";
	}
}
