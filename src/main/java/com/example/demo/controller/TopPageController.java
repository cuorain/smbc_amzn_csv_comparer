package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.ComparisonResult;
import com.example.demo.service.SmbcAmazonCsvCompareService;

import lombok.RequiredArgsConstructor;

/**
 * トップページのController
 * 
 * @author COSMOROOT
 *
 */
@Controller
@RequiredArgsConstructor
public class TopPageController {
	@Autowired
	SmbcAmazonCsvCompareService csvCompareService;
	
	/**
	 * トップページを表示する
	 * @param model モデル
	 * @return トップページ
	 */
	@GetMapping("/")
	public String topPage(Model model) {
		return "topPage";
	}
	
	/**
	 * CSVの照合を行い、結果を表示する
	 * @param smbcCSV 三井住友カードの請求明細CSV
	 * @param amazonCSV Amazonの注文明細CSV
	 * @param model 結果を格納するモデル
	 * @return 結果生成ページ
	 */
	@PostMapping(value = "/", params = "import_file")
	public String showComparingResult(@RequestParam("smbc_import_file") MultipartFile smbcCSV, @RequestParam("amazon_import_file") MultipartFile amazonCSV, Model model) {
		// 結果生成
		final ComparisonResult result = csvCompareService.getCompareResult(smbcCSV, amazonCSV);
		// 結果のマッピング
		model.addAttribute("smbcNotMatched", result.getSmbcNotMatched());
		model.addAttribute("amazonNotMatched", result.getAmazonNotMatched());
		model.addAttribute("matched", result.getMatched());
		return "topPage";
	}
}
