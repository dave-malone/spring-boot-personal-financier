package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.BudgetType;
import io.dmalone.personalfinancier.service.BudgetService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BudgetController {

	private final BudgetService budgetService;
	private static final DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");

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
	
	@RequestMapping("/{forDate}")
	public String index(@PathVariable("forDate") String forDate, Model model) throws Exception{
		Budget budget = budgetService.getBudgetForDate(dateFormat.parse(forDate));
		model.addAttribute("budget", budget);
		
		return "index";
	}
	
	@RequestMapping("/forecast/{numberOfMonths}")
	public String forecast(@PathVariable("numberOfMonths") int numberOfMonths, Model model){
		List<Budget> forecast = budgetService.generateForecast(numberOfMonths);
		model.addAttribute("forecast", forecast);
		model.addAttribute("numberOfMonths", numberOfMonths);
		
		return "forecast";
	}
	
}
