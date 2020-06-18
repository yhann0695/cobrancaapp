package com.cobrancaapp.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cobrancaapp.model.StatusTitulo;
import com.cobrancaapp.model.Titulo;
import com.cobrancaapp.repository.TituloRepository;
import com.cobrancaapp.service.CadastroTituloService;


@Controller
public class TituloController {
	
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
	@Autowired
	private TituloRepository tituloRepository;

	@GetMapping("/cadastroTitulo")
	public ModelAndView novo() {
		ModelAndView model = new ModelAndView("cadastroTitulo");
		model.addObject(new Titulo());
		return model;
	}
	
	@PostMapping(value="**/salvarTitulo")
	public String salvar(@Valid Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if(errors.hasErrors()) {
			return "cadastroTitulo";
		}
		
		try {
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Título salvo!");
			return "redirect:/cadastroTitulo";			
		} catch(DataIntegrityViolationException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return "cadastroTitulo";
		}
		
	}
	
	@GetMapping(value="/pesquisaTitulos")
	public ModelAndView listar() {
		List<Titulo> todosTitulos = cadastroTituloService.listarTitulos();
		ModelAndView model = new ModelAndView("pesquisaTitulos");
		model.addObject("titulos", todosTitulos);
		return model;
	}
	
	@PostMapping(value="**/titulos")
	public ModelAndView pesquisar(@RequestParam("pesquisarTitulo")String descricao) {
		List<Titulo> pesquisas = tituloRepository.findByFirstnameEndsWith(descricao);
		List<Titulo> todosTitulos = cadastroTituloService.listarTitulos();
		ModelAndView model = new ModelAndView("pesquisaTitulos");
		model.addObject("titulos", todosTitulos);
		model.addObject("titulos", pesquisas);
		return model;
	}
	
	@GetMapping(value="/cadastroTitulo/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Long codigo) {
		Titulo titulo = cadastroTituloService.editar(codigo);
		ModelAndView model = new ModelAndView("cadastroTitulo");
		model.addObject(titulo);
		return model;
	}
	
	@PostMapping(value="**/titulos/{codigo}")
	public String excluir(@PathVariable("codigo") Long codigo, RedirectAttributes attributes) {
		cadastroTituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído!");
		return "redirect:/pesquisaTitulos";
	}
	
	@GetMapping(value="/titulos/{codigo}/receber")
	public String receber(@PathVariable Long codigo) {
		cadastroTituloService.receber(codigo);
		return "redirect:/pesquisaTitulos";
	}
	
	@ModelAttribute("statusTitulo")
	public List<StatusTitulo> statusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
}
