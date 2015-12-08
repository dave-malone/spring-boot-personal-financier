package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.BudgetType;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.service.BudgetService;
import io.dmalone.personalfinancier.service.ExpenseService;
import io.dmalone.personalfinancier.service.IncomeService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BudgetController {

	private final BudgetService budgetService;
	private final ExpenseService expenseService;
	private final IncomeService incomeService;

	@Autowired
	public BudgetController(BudgetService budgetService, ExpenseService expenseService, IncomeService incomeService) {
		this.budgetService = budgetService;
		this.incomeService = incomeService;
		this.expenseService = expenseService;
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
			model.addAttribute("message", "This budget was auto generated using your planned expenses and income");
		}
		
		model.addAttribute("currentBudget", currentBudget);
		
		return "index";
	}
	
}
