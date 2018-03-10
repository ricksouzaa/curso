package com.curso.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.curso.model.ResumoLancamento;
import com.curso.model.entity.Categoria_;
import com.curso.model.entity.Lancamento;
import com.curso.model.entity.Lancamento_;
import com.curso.model.entity.Pessoa_;
import com.curso.model.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		Long total = total(lancamentoFilter);
		if (total > 0) {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
			Root<Lancamento> root = criteria.from(Lancamento.class);
			
			Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
			criteria.where(predicates);
			
			TypedQuery<Lancamento> query = em.createQuery(criteria);
			adicionarRestricoesDePaginacao(query, pageable);
			
			return new PageImpl<>(query.getResultList(), pageable, total);
		} else {
			return new PageImpl<>(Arrays.asList(), pageable, total);
		}
	}

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get(Lancamento_.codigo)
				, root.get(Lancamento_.descricao)
				, root.get(Lancamento_.dataVencimento)
				, root.get(Lancamento_.dataPagamento)
				, root.get(Lancamento_.valor)
				, root.get(Lancamento_.tipo)
				, root.get(Lancamento_.categoria).get(Categoria_.nome)
				, root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = em.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(builder.upper(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toUpperCase() + "%"));
		}
		
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		if (!StringUtils.isEmpty(lancamentoFilter.getCategoria())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.categoria).get(Categoria_.nome)), "%" + lancamentoFilter.getCategoria().toLowerCase() + "%"));
		}

		if (!StringUtils.isEmpty(lancamentoFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.pessoa).get(Pessoa_.nome)), "%" + lancamentoFilter.getNome().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return em.createQuery(criteria).getSingleResult();
	}

}