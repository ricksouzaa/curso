package com.curso.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.resource.AbstractResource;
import com.curso.model.LancamentoResumo;
import com.curso.model.ResumoLancamento;
import com.curso.model.entity.Lancamento;
import com.curso.model.filter.LancamentoFilter;
import com.curso.repository.LancamentoRepository;
import com.curso.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource extends AbstractResource<Lancamento, LancamentoRepository, LancamentoService> {

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return repository.filtrar(lancamentoFilter, pageable);
	}

	@GetMapping("/resumir")
	public List<LancamentoResumo> resumir() {
		return repository.findAllBy();
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return repository.resumir(lancamentoFilter, pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Lancamento>> list() {
		return super.list();
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> create(@Valid @RequestBody Lancamento entity, HttpServletResponse response) {
		return super.create(entity, response);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> findOne(@PathVariable Long id) {
		return super.findOne(id);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void delete(@PathVariable Long id) {
		super.delete(id);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> update(@PathVariable Long id, @Valid @RequestBody Lancamento entity) {
		return super.update(id, entity);
	}

}