package io.dmalone.personalfinancier.service;

import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;
import io.dmalone.personalfinancier.repository.IncomeRepository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IncomeService {

	private final IncomeRepository incomeRepository;

	@Autowired
	public IncomeService(IncomeRepository incomeRepository) {
		this.incomeRepository = incomeRepository;
	}

	public <S extends Income> S save(S income) {
		return incomeRepository.save(income);
	}

	public Income findOne(String id) {
		return incomeRepository.findOne(id);
	}

	public boolean exists(String id) {
		return incomeRepository.exists(id);
	}

	public Iterable<Income> findAll() {
		return incomeRepository.findAll();
	}

	public Iterable<Income> findAll(Iterable<String> ids) {
		return incomeRepository.findAll(ids);
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

	public void delete(Iterable<? extends Income> entities) {
		incomeRepository.delete(entities);
	}

	public void deleteAll() {
		incomeRepository.deleteAll();
	}

	public Map<IncomeFrequency, List<Income>> getAllIncomesByFrequency() {
		return incomeRepository.getAllIncomesByFrequency();
	}

	public List<Income> getActiveIncome() {
		return incomeRepository.getActiveIncome();
	}
	
}
