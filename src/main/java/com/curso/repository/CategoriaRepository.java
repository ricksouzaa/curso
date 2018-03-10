package com.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.model.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}