package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.BudgetType;
import io.dmalone.personalfinancier.service.BudgetService;

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

	private final BudgetService budgetService;


	@Autowired
	public BudgetController(BudgetService budgetService) {
		this.budgetService = budgetService;
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
		Budget currentBudget = budgetService.getCurrentBudget();
		if(currentBudget.getId() == null){
			model.addAttribute("message", "This budget was auto-generated using your planned expenses and income");
		}
		
		model.addAttribute("budget", currentBudget);
		
		return "index";
	}
	
}
