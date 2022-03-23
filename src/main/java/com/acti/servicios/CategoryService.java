package com.acti.servicios;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.acti.dao.Categoria;
import com.acti.dao.Producto;
import com.acti.repository.CategoriaRepository;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	CategoriaRepository repCat;

	@Override
	public List<Categoria> listAllCategorias() {

		return repCat.findAll();
	}

	@Override
	public Categoria getCategoria(Long Id) {

		return repCat.getById(Id);
	}

	@Override
	public Categoria createCategoria(Categoria categoria) {
		categoria.setFechaCrecion(new Timestamp(new Date().getTime()));
		categoria.setFechaUltModificacion(new Timestamp(new Date().getTime()));
		
		return repCat.save(categoria);
	}

	@Override
	public Categoria updateCtaegoria(Categoria categoria) {
		Categoria catBd = getCategoria(categoria.getIdCategoria());
		if(catBd == null) {
			return null;
			
		}
		catBd.setCategoriaDescripcion(categoria.getCategoriaDescripcion());
		catBd.setFechaUltModificacion(new Timestamp(new Date().getTime()));
		catBd.setSucursal(categoria.getSucursal());
		catBd.setUsuarioUltModificacion(categoria.getUsuarioUltModificacion());
		
		System.out.println("La catgoria es: "+ catBd);

		return repCat.save(catBd);
	}

	@Override
	public Categoria deleteCategoria(Long Id) {
		Categoria catBd = getCategoria(Id);
		if(catBd == null) {
			return null;
			
		}
		
		repCat.delete(catBd);
		return catBd;
	}

	@Override
	public Categoria updateCategoriaDescripcion(Long Id, String description) {
		Categoria catBd = getCategoria(Id);
		if(catBd == null) {
			return null;
			
		}
		
		catBd.setCategoriaDescripcion(description);
		
		
		return repCat.save(catBd);
	}

}
