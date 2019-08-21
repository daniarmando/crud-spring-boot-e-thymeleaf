package com.crudspringbootethymeleaf.model;

public enum Categoria {
	
	ALIMENTO("Alimento"),
	BEBIDA("Bebida"),
	ELETRONICO("Eletrônico"),
	HIGIENE("Higiene"),
	ROUPA("Roupa");
	
	private String descricao;
	
	Categoria(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}	

}
