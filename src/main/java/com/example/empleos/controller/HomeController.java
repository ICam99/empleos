package com.example.empleos.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.empleos.model.Perfil;
import com.example.empleos.model.Usuario;
import com.example.empleos.model.Vacante;
import com.example.empleos.service.ICategoriasService;
import com.example.empleos.service.IUsuariosService;
import com.example.empleos.service.IVacantesService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("Nombre del usuario: " + username);
		
		for (GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}
		
		if (session.getAttribute("usuario") == null) {
			Usuario usuario = serviceUsuarios.buscarPorUsername(username);
			usuario.setPassword(null);
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de Comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
	}

	@GetMapping("/")
	public String mostrarHome(Model model) {
		return "home";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		
		String pwdPlano = usuario.getPassword();
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncriptado);
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		
		Perfil p = new Perfil();
		p.setId(3);
		
		usuario.agregar(p);
		
		serviceUsuarios.guardar(usuario);
		attributes.addFlashAttribute("msg", "Registro Guardado");
		return "redirect:/usuarios/index";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		
		vacanteSearch.reset();
		
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());

		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		
		model.addAttribute("search", vacanteSearch);
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando: " + vacante);
		
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		
		return "home";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
	}
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/login";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}
