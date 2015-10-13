package io.dmalone.personalfinancier.conversion;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.repository.ExpenseRepository;
import io.dmalone.personalfinancier.repository.IncomeRepository;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Bean
	public Map<? extends Object, ? extends PropertyEditor> customEditors(){
		Map<Object, PropertyEditor> customEditors = new HashMap<Object, PropertyEditor>();
		customEditors.put("java.util.Date", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true){

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if("NULL".equalsIgnoreCase(text)){
					text = "";
				}
				super.setAsText(text);
			}
			
		});
		customEditors.put("java.lang.Integer", new CustomNumberEditor(Integer.class, true){
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if("NULL".equalsIgnoreCase(text)){
					text = "";
				}
				super.setAsText(text);
			}
		});
		return customEditors;
	}
	
	@Bean
    public ItemReader<ExpenseData> expenseItemReader() {
        FlatFileItemReader<ExpenseData> reader = new FlatFileItemReader<ExpenseData>();
        reader.setResource(new ClassPathResource("expense.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<ExpenseData>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "version", "active", "amount", "dueDate", "end", "expenseType", "name", "start" });
                setDelimiter(DELIMITER_TAB);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<ExpenseData>() {{
                setTargetType(ExpenseData.class);
                setCustomEditors(customEditors());
            }});
        }});
        return reader;
    }
	
	@Bean
    public ItemReader<IncomeData> incomeItemReader() {
        FlatFileItemReader<IncomeData> reader = new FlatFileItemReader<IncomeData>();
        reader.setResource(new ClassPathResource("income.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<IncomeData>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "version", "amount", "date", "endDate", "incomeFrequency", "name", "startDate" });
                setDelimiter(DELIMITER_TAB);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<IncomeData>() {{
                setTargetType(IncomeData.class);
                setCustomEditors(customEditors());
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<ExpenseData, Expense> expenseItemProcessor() {
        return new ExpenseItemProcessor();
    }
    
    @Bean
    public ItemProcessor<IncomeData, Income> incomeItemProcessor() {
        return new IncomeItemProcessor();
    }

    @Bean
    public ItemWriter<Expense> expenseItemWriter(ExpenseRepository expenseRepository) {
        ExpenseItemWriter writer = new ExpenseItemWriter(expenseRepository);
        return writer;
    }
    
    @Bean
    public ItemWriter<Income> incomeItemWriter(IncomeRepository incomeRepository) {
        IncomeItemWriter writer = new IncomeItemWriter(incomeRepository);
        return writer;
    }

    @Bean
    public Step importExpensesStep(StepBuilderFactory stepBuilderFactory, ItemReader<ExpenseData> reader, ItemWriter<Expense> writer, ItemProcessor<ExpenseData, Expense> processor) {
        return stepBuilderFactory.get("importExpensesStep")
                .<ExpenseData, Expense> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    
    @Bean
    public Step importIncomeStep(StepBuilderFactory stepBuilderFactory, ItemReader<IncomeData> reader, ItemWriter<Income> writer, ItemProcessor<IncomeData, Income> processor) {
        return stepBuilderFactory.get("importIncomesStep")
                .<IncomeData, Income> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    
    @Bean
    public Job convertPfDataJob(JobBuilderFactory jobs, Step importExpensesStep, Step importIncomeStep) {
        return jobs.get("convertPfDataJob")
                .incrementer(new RunIdIncrementer())
                .flow(importExpensesStep)
                .next(importIncomeStep)
                .end()
                .build();
    }

}
