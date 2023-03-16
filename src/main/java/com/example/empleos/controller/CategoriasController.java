package com.example.empleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.empleos.model.Categoria;
import com.example.empleos.service.ICategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> listaCategorias = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", listaCategorias);
		return "categorias/listCategorias";
	}

	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> lista = serviceCategorias.buscarTodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}

	@GetMapping("/create")
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}

	@PostMapping("/save")
	public String guardar(Categoria categoria, RedirectAttributes attributes, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "categorias/formCategorias";
		}
		
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Registro Guardado");
		System.out.println("Categoria: " + categoria);
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		System.out.println("Borrando categoria con id: " + idCategoria);
		
		serviceCategorias.eliminar(idCategoria);
		
		attributes.addFlashAttribute("msg", "La categoria fue eliminada.");
		
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);
		return "categorias/formCategoria";
	}
}
