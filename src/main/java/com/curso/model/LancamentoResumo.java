package com.curso.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.curso.model.entity.TipoLancamento;

public interface LancamentoResumo {

    String getDescricao();

    LocalDate getDataVencimento();

    BigDecimal getValor();

    TipoLancamento getTipo();

    Categoria getCategoria();

    Pessoa getPessoa();

    interface Categoria {

        String getNome();

    }

    interface Pessoa {

        String getNome();

        Endereco getEndereco();

        interface Endereco {

            String getCidade();

        }
    }
}
