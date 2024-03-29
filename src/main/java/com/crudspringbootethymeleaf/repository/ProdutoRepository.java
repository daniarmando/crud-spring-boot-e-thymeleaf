package com.crudspringbootethymeleaf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crudspringbootethymeleaf.model.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

}
