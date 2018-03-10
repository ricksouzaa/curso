package com.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.core.model.entity.AbstractEntity;

public class AbstractService<E extends AbstractEntity, R extends JpaRepository<E, Long>> {

	@Autowired
	protected R repository;

	public E save(E entity) {
		return repository.save(entity);
	}

	public boolean validateIfExist(Long id) {
		if (id == null || !repository.exists(id)) {
			throw new EmptyResultDataAccessException(1);
		}
		return true;
	}

	public E update(E entity) {
		validateIfExist(entity.getId());
		return save(entity);
	}

	public void delete(Long id) {
		repository.delete(id);
	}

}