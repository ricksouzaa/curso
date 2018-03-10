package com.curso.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.resource.AbstractResource;
import com.curso.model.entity.Categoria;
import com.curso.repository.CategoriaRepository;
import com.curso.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource extends AbstractResource<Categoria, CategoriaRepository, CategoriaService> {

	@Override
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Categoria>> list() {
		return super.list();
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria entity, HttpServletResponse response) {
		return super.create(entity, response);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> findOne(@PathVariable Long id) {
		return super.findOne(id);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CATEGORIA') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		super.delete(id);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> update(@PathVariable Long id, @Valid @RequestBody Categoria entity) {
		return super.update(id, entity);
	}

}