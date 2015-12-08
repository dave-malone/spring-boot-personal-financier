package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;
import io.dmalone.personalfinancier.service.IncomeService;

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
@RequestMapping("/income")
public class IncomeController {

	private final Log log = LogFactory.getLog(IncomeController.class);
	private final IncomeService incomeService;

	@Autowired
	public IncomeController(IncomeService incomeService) {
		this.incomeService = incomeService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		webDataBinder.registerCustomEditor(IncomeFrequency.class, new IncomeFrequencyEnumConverter());
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		Map<IncomeFrequency, List<Income>> incomeByFrequency = incomeService.getAllIncomesByFrequency();
		
		model.addAttribute("incomeByFrequency", incomeByFrequency);
		return "income/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model){
		Income income = new Income();
		
		model.addAttribute("incomeFrequencies", IncomeFrequency.values());
		model.addAttribute("income", income);
		return "income/form";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String create(@PathVariable("id") String id, Model model){
		Income income = incomeService.findOne(id);
		
		if(income == null){
			model.addAttribute("message", "Income for ID " + id + " could not be found");
			return "redirect:/income/";
		}
		
		model.addAttribute("incomeFrequencies", IncomeFrequency.values());
		model.addAttribute("income", income);
		return "income/form";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") String id){
		Income income = incomeService.findOne(id);
		
		if(income == null){
			return "Income for ID " + id + " could not be found";
		}
		
		incomeService.delete(id);
		return "Income " + id + " was deleted";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(Income income, Model model){
		log.debug("Saving income: " + income);
		income = incomeService.save(income);
		model.addAttribute("income", income);
		model.addAttribute("message", "Income Saved");
		
		return "redirect:/income/";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public String update(Income income, Model model){
		log.debug("Updating income: " + income);
		income = incomeService.save(income);
		model.addAttribute("income", income);
		model.addAttribute("message", "Income Updated");
		
		return "redirect:/income/";
	}
	
}
