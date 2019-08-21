package com.crudspringbootethymeleaf.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudspringbootethymeleaf.model.Produto;
import com.crudspringbootethymeleaf.repository.ProdutoRepository;
import com.crudspringbootethymeleaf.service.exception.ProdutoNaoEncontradoException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public void salvar(Produto produto) {
		produtoRepository.save(produto);
	}
	
	public Iterable<Produto> todos() {
		return produtoRepository.findAll(); 		
	}
	
	public Produto porCodigo(Long codigo) {		
		Optional<Produto> produto = produtoRepository.findById(codigo);
		
		if (produto.isPresent()) {
			return produto.get();
		} else {
			throw new ProdutoNaoEncontradoException("Não encontrado produto com o código: " + codigo);
		}
	}
	
	public void excluir(Long codigo) throws ProdutoNaoEncontradoException {		
		Produto produto = porCodigo(codigo);		
		produtoRepository.delete(produto);		
	}

}
