package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.BudgetType;
import io.dmalone.personalfinancier.repository.BudgetRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BudgetController {

	private final BudgetRepository budgetRepository;

	@Autowired
	public BudgetController(BudgetRepository budgetRepository) {
		this.budgetRepository = budgetRepository;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		webDataBinder.registerCustomEditor(BudgetType.class, new BudgetTypeEnumConverter());
	}
	
	@RequestMapping("/")
	public String index(Model model){
		model.addAttribute("name", "World!");
		return "index";
	}
	
}
