package io.dmalone.personalfinancier.service;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.repository.ExpenseRepository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

	private final ExpenseRepository expenseRepository;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public Map<ExpenseType, List<Expense>> getAllExpensesByType() {
		return expenseRepository.getAllExpensesByType();
	}

	public List<Expense> findByExpenseType(ExpenseType expenseType) {
		return expenseRepository.findByExpenseType(expenseType);
	}

	public <S extends Expense> S save(S entity) {
		return expenseRepository.save(entity);
	}

	public <S extends Expense> List<S> save(Iterable<S> entites) {
		return expenseRepository.save(entites);
	}

	public Page<Expense> findAll(Pageable pageable) {
		return expenseRepository.findAll(pageable);
	}

	public List<Expense> findAll() {
		return expenseRepository.findAll();
	}

	public Expense findOne(String id) {
		return expenseRepository.findOne(id);
	}

	public List<Expense> findAll(Sort sort) {
		return expenseRepository.findAll(sort);
	}

	public boolean exists(String id) {
		return expenseRepository.exists(id);
	}

	public Iterable<Expense> findAll(Iterable<String> ids) {
		return expenseRepository.findAll(ids);
	}

	public long count() {
		return expenseRepository.count();
	}

	public void delete(Expense entity) {
		expenseRepository.delete(entity);
	}

	public void deleteAll() {
		expenseRepository.deleteAll();
	}

	public void delete(String id) {
		expenseRepository.delete(id);
	}

	public List<Expense> getActiveExpenses() {
		return expenseRepository.getActiveExpenses();
	}
	
}
