package com.algaworks;


import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatosControle {

	private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();
	
	static {
		LISTA_CONTATOS.add(new Contato("1", "Eduardo", "+55 11 94286 9687"));
		LISTA_CONTATOS.add(new Contato("2", "Dezenatty", "+55 11 94286 9687"));
		LISTA_CONTATOS.add(new Contato("3", "Sarah", "+55 11 94286 9687"));
		LISTA_CONTATOS.add(new Contato("4", "jULIO", "+55 11 94286 9687"));
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/contatos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("listar");
		modelAndView.addObject("contatos", LISTA_CONTATOS);
		return modelAndView;	
	}
	
	@GetMapping("/contatos/novo")
	public ModelAndView novo() {
		ModelAndView modelAndView = new ModelAndView("formulario");
		modelAndView.addObject("contato", new Contato());
		return modelAndView;	
	} 
	
	@PostMapping("/contatos")
	public String cadastrar(Contato contato) {
		String id = UUID.randomUUID().toString();
		contato.setId(id);
		LISTA_CONTATOS.add(contato);
		return "redirect:/contatos";	
	}
	
	@GetMapping("/contatos/{id}/editar")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("formulario");
		
		Contato contato = procurarContato(id);
		modelAndView.addObject("contato", contato);
		return modelAndView;	
	} 
	
	@RequestMapping("/contatos/{id}")
	public String atualizar(Contato contato) {
		Integer indice = procurarIndiceContato(contato.getId());
		
		Contato contatoVelho= LISTA_CONTATOS.get(indice);
		
		LISTA_CONTATOS.remove(contatoVelho);
		LISTA_CONTATOS.add(indice, contato);
		
		return "redirect:/contatos";
		
	}
	
	// --- Métodos auxiliares
	
	private Contato procurarContato(String id) {
		for (int i = 0 ; i < LISTA_CONTATOS.size() ; i++) {
			Contato contato = LISTA_CONTATOS.get(i);
			
			if (contato.getId().equals(id)) {
				return contato;
			}
		}	
		return null;
	}
	
	
	private Integer procurarIndiceContato(String id) {
		
		for (int i = 0 ; i < LISTA_CONTATOS.size() ; i++) {
			Contato contato = LISTA_CONTATOS.get(i);
			
			if (contato.getId().equals(id)) {
				return i;
			}
		}
		return null;
	}
	

}
