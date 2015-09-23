package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.repository.ExpenseRepository;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

	private final ExpenseRepository expenseRepository;
	private final Log log = LogFactory.getLog(ExpenseController.class);

	@Autowired
	public ExpenseController(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		Map<ExpenseType, List<Expense>> expensesByType = expenseRepository.getAllExpensesByType();
		
		model.addAttribute("expensesByType", expensesByType);
		return "expense/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model){
		Expense expense = new Expense();
		
		model.addAttribute("expenseTypes", ExpenseType.values());
		model.addAttribute("expense", expense);
		return "expense/form";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String create(@PathVariable("id") String id, Model model){
		Expense expense = expenseRepository.findOne(id);
		
		if(expense == null){
			model.addAttribute("message", "Expense for ID " + id + " could not be found");
			return "redirect:/expense/";
		}
		
		model.addAttribute("expenseTypes", ExpenseType.values());
		model.addAttribute("expense", expense);
		return "expense/form";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(Expense expense, Model model){
		log.debug(expense);
		expense = expenseRepository.save(expense);
		model.addAttribute("expense", expense);
		model.addAttribute("message", "Expense Saved");
		
		return "redirect:/expense/";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public String update(Expense expense, Model model){
		log.debug(expense);
		expense = expenseRepository.save(expense);
		model.addAttribute("expense", expense);
		model.addAttribute("message", "Expense Updated");
		
		return "redirect:/expense/";
	}
	
}
