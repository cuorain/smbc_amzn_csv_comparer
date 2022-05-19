package com.example.demo.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.BillingStatementList;
import com.example.demo.model.SmbcCsvReader;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TopPageController {
	@GetMapping("/")
	public String topPage(Model model) {
		model.addAttribute("elemId", "test");
		return "topPage";
	}
	
	@PostMapping(value = "/", params = "import_file")
	public String runCheckCSVDifference(@RequestParam("smbc_import_file") MultipartFile smbcCSV, @RequestParam("smbc_import_file") MultipartFile amazonCSV) {
		final SmbcCsvReader reader = new SmbcCsvReader();
		final List<String[]> smbcRawData = reader.read(smbcCSV);
		final BillingStatementList smbcData = new BillingStatementList(smbcRawData);
		return "topPage";
	}
}
