package com.core.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.core.event.ResourceCreatedEvent;
import com.core.model.entity.AbstractEntity;
import com.core.service.AbstractService;

public abstract class AbstractResource<E extends AbstractEntity, R extends JpaRepository<E, Long>, S extends AbstractService<E, R>> {

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected ApplicationEventPublisher publisher;

	@Autowired
	protected R repository;

	@Autowired
	protected S service;

	@GetMapping(params = "all") 
	public ResponseEntity<List<E>> list() {
		List<E> categorias = repository.findAll();
		return ResponseEntity.ok(categorias);
	}

	@PostMapping
	public ResponseEntity<E> create(@Valid @RequestBody E entity, HttpServletResponse response) {
		E savedEntity = service.save(entity);

		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedEntity.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
	}

	@GetMapping("/{id}")
	public ResponseEntity<E> findOne(@PathVariable Long id) {
		E entity = repository.findOne(id);
		return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<E> update(@PathVariable Long id, @Valid @RequestBody E entity) {
		return ResponseEntity.ok(service.update(entity));
	}

}
