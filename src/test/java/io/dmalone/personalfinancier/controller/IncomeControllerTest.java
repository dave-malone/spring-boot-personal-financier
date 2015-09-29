package io.dmalone.personalfinancier.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import io.dmalone.personalfinancier.PersonalfinancierApplication;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;
import io.dmalone.personalfinancier.repository.IncomeRepository;

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
public class IncomeControllerTest {
    
	private MockMvc mvc;
	private IncomeRepository incomeRepository;

    @Before
    public void setUp() throws Exception {
    	incomeRepository = mock(IncomeRepository.class);
        mvc = MockMvcBuilders.standaloneSetup(
                new IncomeController(incomeRepository))
            .build();
    }

    @Test
    public void getIncomesByType() throws Exception {
        mvc.perform(
        		MockMvcRequestBuilders
        			.get("/income/"))
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("incomeByFrequency"))
	            .andExpect(forwardedUrl("income/list"));
    }
    
    @Test
    public void createIncome() throws Exception {
        mvc.perform(
        		MockMvcRequestBuilders
        			.get("/income/create"))
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("income"))
	            .andExpect(model().attributeExists("incomeFrequencies"))
	            .andExpect(forwardedUrl("income/form"));
    }
    
    @Test
    public void editIncomeHappyPath() throws Exception {
    	String incomeId = "123456789";
    	
    	IncomeFrequency incomeFrequency = IncomeFrequency.BiWeekly;
		String name = "Test Income";
		String amount = "5.00";
		
		Income expectedIncome = new Income(name, amount, incomeFrequency);
		expectedIncome.setId(incomeId);
		
		when(incomeRepository.findOne(incomeId)).thenReturn(expectedIncome);
    	
        mvc.perform(
        		MockMvcRequestBuilders
        			.get("/income/edit/" + incomeId))
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("income"))
	            .andExpect(model().attributeExists("incomeFrequencies"))
	            .andExpect(model().attribute("income", hasProperty("incomeFrequency", is(incomeFrequency))))
	            .andExpect(model().attribute("income", hasProperty("name", is(name))))
	            .andExpect(model().attribute("income", hasProperty("amount", is(new BigDecimal(amount)))))
	            .andExpect(model().attribute("income", hasProperty("id", is(incomeId))))
	            .andExpect(forwardedUrl("income/form"));
    }
    
    @Test
    public void deleteIncomeHappyPath() throws Exception {
    	String incomeId = "123456789";
    	
    	IncomeFrequency incomeFrequency = IncomeFrequency.BiWeekly;
		String name = "Test Income";
		String amount = "5.00";
		
		Income expectedIncome = new Income(name, amount, incomeFrequency);
		expectedIncome.setId(incomeId);
		
		when(incomeRepository.findOne(incomeId)).thenReturn(expectedIncome);
    	
        mvc.perform(
        		MockMvcRequestBuilders
        			.delete("/income/" + incomeId))
	            .andExpect(status().isOk())
	            .andExpect(content().string(
	                    is("Income " + incomeId + " was deleted")));
    }
    
    @Test
    public void deleteUnknownIncomeIdReturnsAppropriateMessage() throws Exception {
    	String incomeId = "123456789";
		
		Income expectedIncome = null;
		
		when(incomeRepository.findOne(incomeId)).thenReturn(expectedIncome);
    	
        mvc.perform(
        		MockMvcRequestBuilders
        			.delete("/income/" + incomeId))
	            .andExpect(status().isOk())
	            .andExpect(content().string(
	                    is("Income for ID " + incomeId + " could not be found")));
    }
    
    @Test
    public void saveIncomeHappyPath() throws Exception {
        IncomeFrequency incomeFrequency = IncomeFrequency.BiWeekly;
		String name = "Test Income";
		String amount = "5.00";
		String startDate = "09/28/2015";
		
		Income expectedIncome = new Income(name, amount, incomeFrequency);
		when(incomeRepository.save(expectedIncome)).thenReturn(expectedIncome);
		
		mvc.perform(
        		MockMvcRequestBuilders
        			.post("/income/")
		            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
		            .param("incomeFrequency", incomeFrequency.toString())
		            .param("name", name)
		            .param("amount", amount)
		            .param("startDate", startDate))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(model().attributeExists("income"))
	            .andExpect(model().attributeExists("message"))
	            .andExpect(model().attribute("message", is("Income Saved")))
	            .andExpect(model().attribute("income", hasProperty("incomeFrequency", is(incomeFrequency))))
	            .andExpect(model().attribute("income", hasProperty("name", is(name))))
	            .andExpect(model().attribute("income", hasProperty("amount", is(new BigDecimal(amount)))))
	            .andExpect(redirectedUrl("/income/?message=Income+Saved"));
    }
    
    
    
    @Test
    public void updateIncomeHappyPath() throws Exception {
        IncomeFrequency incomeFrequency = IncomeFrequency.BiWeekly;
		String name = "Test Income";
		String amount = "5.00";
		String incomeId = "123456789";
		
		Income expectedIncome = new Income(name, amount, incomeFrequency);
		expectedIncome.setId(incomeId);
		when(incomeRepository.save(expectedIncome)).thenReturn(expectedIncome);
		
		mvc.perform(
        		MockMvcRequestBuilders
        			.put("/income/")
		            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
		            .param("id", incomeId)
		            .param("incomeFrequency", incomeFrequency.toString())
		            .param("name", name)
		            .param("amount", amount))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(model().attributeExists("income"))
	            .andExpect(model().attributeExists("message"))
	            .andExpect(model().attribute("message", is("Income Updated")))
	            .andExpect(model().attribute("income", hasProperty("incomeFrequency", is(incomeFrequency))))
	            .andExpect(model().attribute("income", hasProperty("name", is(name))))
	            .andExpect(model().attribute("income", hasProperty("amount", is(new BigDecimal(amount)))))
	            .andExpect(model().attribute("income", hasProperty("id", is(incomeId))))
	            .andExpect(redirectedUrl("/income/?message=Income+Updated"));
    }
}
