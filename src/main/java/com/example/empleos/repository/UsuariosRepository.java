package com.example.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empleos.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
