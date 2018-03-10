package com.curso.service;

import org.springframework.stereotype.Service;

import com.core.service.AbstractService;
import com.curso.model.entity.Pessoa;
import com.curso.repository.PessoaRepository;

@Service
public class PessoaService extends AbstractService<Pessoa, PessoaRepository> {

	public void ativarDesativar(Long id, boolean ativo) {
		if (validateIfExist(id)) {
			Pessoa pessoa = repository.findOne(id);
			pessoa.setAtivo(ativo);
			save(pessoa);
		}
	}

}