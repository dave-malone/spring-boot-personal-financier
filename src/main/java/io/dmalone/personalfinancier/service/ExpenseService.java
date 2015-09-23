package io.dmalone.personalfinancier.service;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExpenseService {

	private final ExpenseRepository expenseRepository;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public <S extends Expense> S save(S entity) {
		return expenseRepository.save(entity);
	}

	public <S extends Expense> Iterable<S> save(Iterable<S> entities) {
		return expenseRepository.save(entities);
	}

	public Expense findOne(String id) {
		return expenseRepository.findOne(id);
	}

	public boolean exists(String id) {
		return expenseRepository.exists(id);
	}

	public Iterable<Expense> findAll() {
		return expenseRepository.findAll();
	}

	public Iterable<Expense> findAll(Iterable<String> ids) {
		return expenseRepository.findAll(ids);
	}

	public long count() {
		return expenseRepository.count();
	}

	public void delete(String id) {
		expenseRepository.delete(id);
	}

	public void delete(Expense entity) {
		expenseRepository.delete(entity);
	}

	public void delete(Iterable<? extends Expense> entities) {
		expenseRepository.delete(entities);
	}

	public void deleteAll() {
		expenseRepository.deleteAll();
	}

	
	
}
