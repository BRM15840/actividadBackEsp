package com.acti.servicios;

import java.util.List;

import com.acti.dao.*;

public interface ICategoryService {

	public List<Categoria> listAllCategorias();
	
	public Categoria getCategoria(Long Id);
	
	public Categoria createCategoria(Categoria categoria);
	
	public Categoria updateCtaegoria(Categoria categoria);
	
	public Categoria deleteCategoria(Long Id);

	public Categoria updateCategoriaDescripcion(Long Id, String description);
}
