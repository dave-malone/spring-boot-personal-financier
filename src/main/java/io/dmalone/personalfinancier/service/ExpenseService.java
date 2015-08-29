package io.dmalone.personalfinancier.service;

import java.util.List;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ExpenseService {

	private final ExpenseRepository expenseRepository;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public <S extends Expense> S save(S entity) {
		//TODO - after saving an expense, make sure that it is sent to the budget service
		return expenseRepository.save(entity);
	}

	public <S extends Expense> List<S> save(Iterable<S> entites) {
		//TODO - after saving an expense, make sure that it is sent to the budget service
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

	public long count() {
		return expenseRepository.count();
	}

	public void delete(String id) {
		expenseRepository.delete(id);
	}

	public void delete(Expense entity) {
		expenseRepository.delete(entity);
	}
	
}
