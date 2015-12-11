package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.service.ExpenseService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

	private final ExpenseService expenseService;
	private final Log log = LogFactory.getLog(ExpenseController.class);

	@Autowired
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		webDataBinder.registerCustomEditor(ExpenseType.class, new ExpenseTypeEnumConverter());
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		Map<ExpenseType, List<Expense>> expensesByType = expenseService.getAllActiveExpensesByType();
		
		model.addAttribute("expensesByType", expensesByType);
		return "expense/list";
	}

	
	@RequestMapping(value = "/inactive", method = RequestMethod.GET)
	public String inactiveExpenses(Model model){
		Map<ExpenseType, List<Expense>> expensesByType = expenseService.getAllInactiveExpensesByType();
		
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
		Expense expense = expenseService.findOne(id);
		
		if(expense == null){
			model.addAttribute("message", "Expense for ID " + id + " could not be found");
			return "redirect:/expense/";
		}
		
		model.addAttribute("expenseTypes", ExpenseType.values());
		model.addAttribute("expense", expense);
		return "expense/form";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") String id){
		Expense expense = expenseService.findOne(id);
		
		if(expense == null){
			return "Expense for ID " + id + " could not be found";
		}
		
		expenseService.delete(id);
		return "Expense " + id + " was deleted";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(Expense expense, Model model){
		log.debug(expense);
		expense = expenseService.save(expense);
		model.addAttribute("expense", expense);
		model.addAttribute("message", "Expense Saved");
		
		return "redirect:/expense/";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public String update(Expense expense, Model model){
		log.debug(expense);
		expense = expenseService.save(expense);
		model.addAttribute("expense", expense);
		model.addAttribute("message", "Expense Updated");
		
		return "redirect:/expense/";
	}
	
}
