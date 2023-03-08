package com.example.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empleos.model.Categoria;

//public interface CategoriasRepository extends CrudRepository<Categoria, Integer> {
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {

}
