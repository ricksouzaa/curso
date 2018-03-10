package com.curso.service;

import org.springframework.stereotype.Service;

import com.core.service.AbstractService;
import com.curso.model.entity.Categoria;
import com.curso.repository.CategoriaRepository;

@Service
public class CategoriaService extends AbstractService<Categoria, CategoriaRepository> {

}