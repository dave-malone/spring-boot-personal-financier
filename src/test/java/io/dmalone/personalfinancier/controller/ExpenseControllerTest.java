package io.dmalone.personalfinancier.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.dmalone.personalfinancier.PersonalfinancierApplication;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.service.ExpenseService;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersonalfinancierApplication.class)
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class ExpenseControllerTest {
    
	private MockMvc mvc;
	private ExpenseService expenseService;

    @Before
    public void setUp() throws Exception {
    	expenseService = mock(ExpenseService.class);
        mvc = MockMvcBuilders.standaloneSetup(
                new ExpenseController(expenseService))
            .build();
    }

    @Test
    public void getExpensesByType() throws Exception {
        mvc.perform(
        		MockMvcRequestBuilders
        			.get("/expense/"))
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("expensesByType"))
	            .andExpect(forwardedUrl("expense/list"));
    }
    
    @Test
    public void createExpense() throws Exception {
        mvc.perform(
        		MockMvcRequestBuilders
        			.get("/expense/create"))
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("expense"))
	            .andExpect(model().attributeExists("expenseTypes"))
	            .andExpect(forwardedUrl("expense/form"));
    }
    
    @Test
    public void editExpenseHappyPath() throws Exception {
    	String expenseId = "123456789";
    	
    	ExpenseType expenseType = ExpenseType.Monthly;
		String name = "Test Expense";
		String amount = "5.00";
		
		Expense expectedExpense = new Expense(name, amount, expenseType);
		expectedExpense.setId(expenseId);
		
		when(expenseService.findOne(expenseId)).thenReturn(expectedExpense);
    	
        mvc.perform(
        		MockMvcRequestBuilders
        			.get("/expense/edit/" + expenseId))
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("expense"))
	            .andExpect(model().attributeExists("expenseTypes"))
	            .andExpect(model().attribute("expense", hasProperty("expenseType", is(expenseType))))
	            .andExpect(model().attribute("expense", hasProperty("name", is(name))))
	            .andExpect(model().attribute("expense", hasProperty("amount", is(new BigDecimal(amount)))))
	            .andExpect(model().attribute("expense", hasProperty("id", is(expenseId))))
	            .andExpect(forwardedUrl("expense/form"));
    }
    
    @Test
    public void deleteExpenseHappyPath() throws Exception {
    	String expenseId = "123456789";
    	
    	ExpenseType expenseType = ExpenseType.Monthly;
		String name = "Test Expense";
		String amount = "5.00";
		
		Expense expectedExpense = new Expense(name, amount, expenseType);
		expectedExpense.setId(expenseId);
		
		when(expenseService.findOne(expenseId)).thenReturn(expectedExpense);
    	
        mvc.perform(
        		MockMvcRequestBuilders
        			.delete("/expense/" + expenseId))
	            .andExpect(status().isOk())
	            .andExpect(content().string(
	                    is("Expense " + expenseId + " was deleted")));
    }
    
    @Test
    public void deleteUnknownExpenseIdReturnsAppropriateMessage() throws Exception {
    	String expenseId = "123456789";
		
		Expense expectedExpense = null;
		
		when(expenseService.findOne(expenseId)).thenReturn(expectedExpense);
    	
        mvc.perform(
        		MockMvcRequestBuilders
        			.delete("/expense/" + expenseId))
	            .andExpect(status().isOk())
	            .andExpect(content().string(
	                    is("Expense for ID " + expenseId + " could not be found")));
    }
    
    @Test
    public void saveExpenseHappyPath() throws Exception {
        ExpenseType expenseType = ExpenseType.PerPaycheck;
		String name = "Test Expense";
		String amount = "5.00";
		String startDate = "09/28/2015";
		
		Expense expectedExpense = new Expense(name, amount, expenseType);
		when(expenseService.save(expectedExpense)).thenReturn(expectedExpense);
		
		mvc.perform(
        		MockMvcRequestBuilders
        			.post("/expense/")
		            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
		            .param("expenseType", expenseType.toString())
		            .param("name", name)
		            .param("amount", amount)
		            .param("startDate", startDate))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(model().attributeExists("expense"))
	            .andExpect(model().attributeExists("message"))
	            .andExpect(model().attribute("message", is("Expense Saved")))
	            .andExpect(model().attribute("expense", hasProperty("expenseType", is(expenseType))))
	            .andExpect(model().attribute("expense", hasProperty("name", is(name))))
	            .andExpect(model().attribute("expense", hasProperty("amount", is(new BigDecimal(amount)))))
	            .andExpect(redirectedUrl("/expense/?message=Expense+Saved"));
    }
    
    
    
    @Test
    public void updateExpenseHappyPath() throws Exception {
        ExpenseType expenseType = ExpenseType.Monthly;
		String name = "Test Expense";
		String amount = "5.00";
		String expenseId = "123456789";
		
		Expense expectedExpense = new Expense(name, amount, expenseType);
		expectedExpense.setId(expenseId);
		when(expenseService.save(expectedExpense)).thenReturn(expectedExpense);
		
		mvc.perform(
        		MockMvcRequestBuilders
        			.put("/expense/")
		            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
		            .param("id", expenseId)
		            .param("expenseType", expenseType.toString())
		            .param("name", name)
		            .param("amount", amount))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(model().attributeExists("expense"))
	            .andExpect(model().attributeExists("message"))
	            .andExpect(model().attribute("message", is("Expense Updated")))
	            .andExpect(model().attribute("expense", hasProperty("expenseType", is(expenseType))))
	            .andExpect(model().attribute("expense", hasProperty("name", is(name))))
	            .andExpect(model().attribute("expense", hasProperty("amount", is(new BigDecimal(amount)))))
	            .andExpect(model().attribute("expense", hasProperty("id", is(expenseId))))
	            .andExpect(redirectedUrl("/expense/?message=Expense+Updated"));
    }
}
