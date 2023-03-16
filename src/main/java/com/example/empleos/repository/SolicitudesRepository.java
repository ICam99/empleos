package com.example.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empleos.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
