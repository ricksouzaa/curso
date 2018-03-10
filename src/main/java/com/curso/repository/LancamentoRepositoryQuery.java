package com.curso.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.model.ResumoLancamento;
import com.curso.model.entity.Lancamento;
import com.curso.model.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}