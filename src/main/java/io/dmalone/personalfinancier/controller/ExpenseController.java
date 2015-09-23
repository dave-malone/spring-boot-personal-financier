package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.service.ExpenseService;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

	private final ExpenseService expenseService;
	private final Log log = LogFactory.getLog(ExpenseController.class);

	@Autowired
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		Map<ExpenseType, List<Expense>> expensesByType = expenseService.getAllExpensesByType();
		
		model.addAttribute("expensesByType", expensesByType);
		return "expenses";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model){
		Expense expense = new Expense();
		
		model.addAttribute("expenseTypes", ExpenseType.values());
		model.addAttribute("expense", expense);
		return "newExpense";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(Expense expense, Model model){
		log.debug(expense);
		expense = expenseService.save(expense);
		model.addAttribute("expense", expense);
		model.addAttribute("message", "Expense Saved");
		
		return "expenses";
	}
	
}
