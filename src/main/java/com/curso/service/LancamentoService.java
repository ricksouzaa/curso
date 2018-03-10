package com.curso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.exception.ServiceException;
import com.core.service.AbstractService;
import com.curso.model.entity.Lancamento;
import com.curso.model.entity.Pessoa;
import com.curso.repository.LancamentoRepository;
import com.curso.repository.PessoaRepository;

@Service
public class LancamentoService extends AbstractService<Lancamento, LancamentoRepository> {

	public static final String PESSOA_INEXISTENTE_OU_INATIVA = "pessoa.inexistente-ou-inativa";

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public Lancamento save(Lancamento entity) {
		Pessoa pessoa = pessoaRepository.findOne(entity.getPessoa().getId());
		if (pessoa == null || pessoa.isInativo()) {
			throw new ServiceException(PESSOA_INEXISTENTE_OU_INATIVA);
		}
		return super.save(entity);
	}
	
}