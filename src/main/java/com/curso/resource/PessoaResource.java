package com.curso.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.core.resource.AbstractResource;
import com.curso.model.entity.Pessoa;
import com.curso.repository.PessoaRepository;
import com.curso.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource extends AbstractResource<Pessoa, PessoaRepository, PessoaService> {

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public Page<Pessoa> pesquisar(@RequestParam(required = false, defaultValue = "%") String nome, Pageable pageable) {
		return repository.findByNomeContainingIgnoreCase(nome, pageable);
	}

	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_PESSOA') and #oauth2.hasScope('write')")
	public void ativarDesativar(@PathVariable Long id, @RequestBody Boolean ativo) {
		service.ativarDesativar(id, ativo);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Pessoa>> list() {
		return super.list();
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa entity, HttpServletResponse response) {
		return super.create(entity, response);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> findOne(@PathVariable Long id) {
		return super.findOne(id);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		super.delete(id);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> update(@PathVariable Long id, @Valid @RequestBody Pessoa entity) {
		return super.update(id, entity);
	}

}