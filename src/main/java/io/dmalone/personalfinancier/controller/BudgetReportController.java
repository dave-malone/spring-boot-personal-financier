package io.dmalone.personalfinancier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BudgetReportController {

	@RequestMapping("/")
	public String index(Model model){
		model.addAttribute("name", "World!");
		return "index";
	}
	
}
