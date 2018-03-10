package com.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}