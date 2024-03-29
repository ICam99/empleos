package com.example.empleos.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.empleos.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService {

	private List<Categoria> lista = null;
	
	public CategoriasServiceImpl() {
		lista = new LinkedList<Categoria>();
		
		Categoria cat1= new Categoria();
		cat1.setId(1);		
		cat1.setNombre("Contabilidad");
		cat1.setDescripcion("Descripcion de la categoria de contabilidad");


		Categoria cat2= new Categoria();
		cat2.setId(2);		
		cat2.setNombre("Ventas");
		cat2.setDescripcion("Trabajos relacionados con ventas");

		
		Categoria cat3= new Categoria();
		cat3.setId(3);		
		cat3.setNombre("Comunicaciones");
		cat3.setDescripcion("Trabajos relacionados con Comunicaciones");
		
		
		Categoria cat4= new Categoria();
		cat4.setId(4);		
		cat4.setNombre("Arquitectura");
		cat4.setDescripcion("Trabajos relacionados con la arquitectura");
		
		Categoria cat5= new Categoria();
		cat5.setId(5);		
		cat5.setNombre("Educacion");
		cat5.setDescripcion("Maestros, tutores, etc.");
		
		Categoria cat6= new Categoria();
		cat6.setId(6);		
		cat6.setNombre("Desarrollo de Software");
		cat6.setDescripcion("Trabajo para programadores");

 		lista.add(cat1);
 		lista.add(cat2);
 		lista.add(cat3);
 		lista.add(cat4);
 		lista.add(cat5);
 		lista.add(cat6);
	}

	@Override
	public void guardar(Categoria categoria) {
		lista.add(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		return lista;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {

		for (Categoria c: lista) {
			if (c.getId()==idCategoria) {
				return c;
			}
		}
		
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
