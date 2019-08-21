package com.crudspringbootethymeleaf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crudspringbootethymeleaf.model.Categoria;
import com.crudspringbootethymeleaf.model.Produto;
import com.crudspringbootethymeleaf.service.ProdutoService;
import com.crudspringbootethymeleaf.service.exception.ProdutoNaoEncontradoException;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {	
	
	@Autowired
	ProdutoService produtoService;
	
	@GetMapping()
	public ModelAndView listarTodosProdutos() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("produtos", produtoService.todos());
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Produto produto) {
		ModelAndView mv = new ModelAndView("cadastro-produto");
		mv.addObject("categorias", Categoria.values());
		return mv;
	}	
	
	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {					
			return novo(produto);
		}
		
		produtoService.salvar(produto);			
		attributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso");				
		return new ModelAndView("redirect:/produtos/novo");
	}
	
	@GetMapping("/editar/{codigo}")
	public String prepararEdicao(@PathVariable("codigo") Long codigo, Model model, RedirectAttributes attributes) {
		try {
			Produto produto = produtoService.porCodigo(codigo);
			model.addAttribute("categorias", Categoria.values());
			model.addAttribute("produto", produto); 
			return "editar-produto";
			
		} catch (ProdutoNaoEncontradoException e) {
			attributes.addFlashAttribute("mensagem", e.getMessage());
			return "redirect:/produtos";
		}							
	}
	
	@PutMapping("/editar/{codigo}")
	public String editar(@PathVariable("codigo") Long codigo, @Valid Produto produto, BindingResult result, 
			Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {	
			model.addAttribute("categorias", Categoria.values());
			return "editar-produto";
		}
		
		produtoService.salvar(produto);	
		attributes.addFlashAttribute("mensagem", "Produto alterado com sucesso");
		return "redirect:/produtos/editar/" + produto.getCodigo();
	}
	
	@GetMapping("/excluir/{codigo}")
	public ModelAndView excluir(@PathVariable("codigo") Long codigo, Model model, RedirectAttributes attributes) {
		try {
			produtoService.excluir(codigo);
			return listarTodosProdutos();
		} catch (ProdutoNaoEncontradoException e) {
			attributes.addFlashAttribute("mensagem", e.getMessage());
			return new ModelAndView("redirect:/produtos");
		}				
	}

}
