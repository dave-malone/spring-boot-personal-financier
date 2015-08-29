package io.dmalone.personalfinancier.service;

import java.util.List;

import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.repository.IncomeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class IncomeService {

	private final IncomeRepository incomeRepository;

	@Autowired
	public IncomeService(IncomeRepository incomeRepository) {
		this.incomeRepository = incomeRepository;
	}
	
	public <S extends Income> S save(S entity) {
		//TODO - after saving an income, make sure that it is sent to the budget service
		return incomeRepository.save(entity);
	}

	public <S extends Income> List<S> save(Iterable<S> entites) {
		//TODO - after saving an income, make sure that it is sent to the budget service
		return incomeRepository.save(entites);
	}

	public Page<Income> findAll(Pageable pageable) {
		return incomeRepository.findAll(pageable);
	}

	public List<Income> findAll() {
		return incomeRepository.findAll();
	}

	public Income findOne(String id) {
		return incomeRepository.findOne(id);
	}

	public long count() {
		return incomeRepository.count();
	}

	public void delete(String id) {
		incomeRepository.delete(id);
	}

	public void delete(Income entity) {
		incomeRepository.delete(entity);
	}
	
}
