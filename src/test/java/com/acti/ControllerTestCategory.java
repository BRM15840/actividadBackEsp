package com.acti;

import java.util.HashMap;
import java.util.List;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.acti.controller.CategoriasController;
import com.acti.controller.CategoriasController;
import com.acti.dao.Categoria;
import com.acti.dao.Categoria;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerTestCategory {
	
	private final Long id = 20L;
	
	@Autowired 
	private CategoriasController controller;
	
	private MapBindingResult bind = new MapBindingResult(new HashMap<>(), "");
	
	@Test
	@Order(1)
	public void pruebaObtenerCategorias(){
		ResponseEntity<List<Categoria>> resp=controller.findAll();
		List<Categoria> listCat= resp.getBody();
		Assertions.assertThat(listCat).size().isEqualTo(3);
		Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}
	
	@Test
	@Order(2)
	public void pruebaAgregarCategoria() {
		Categoria catPrueba = Categoria.builder()
				.categoriaDescripcion("Categoria JUnit")
				.usuarioCreacion("Victor")
				.usuarioUltModificacion("Test")
				.numeroTarea("3").sucursal("123").build();
		
		
		ResponseEntity<Categoria> resp = controller.createCategory(catPrueba, null);
		System.out.println("La categoria creada es: " + resp.getBody().getIdCategoria());

		Assertions.assertThat(resp.getBody().getCategoriaDescripcion()).isEqualTo("Categoria JUnit");
		Assertions.assertThat(resp.getBody().getIdCategoria()).isNotEqualTo(null);
		
		
	}
	@Test
	@Order(3)
	public void pruebaUpdateCategoria() {
		Categoria prodPrueba = Categoria.builder()
				.idCategoria(id)
				.categoriaDescripcion("Categoria JUnit Modificado")
				.usuarioUltModificacion("TestMo")
				.numeroTarea("3").sucursal("123").build()
				;
		
		System.out.println("El id del Categoria es: "+ id);
		ResponseEntity<Categoria> resp = controller.updateCategory(id, prodPrueba);
		
		System.out.println(resp.getBody());
		
		
		Assertions.assertThat(resp.getBody().getCategoriaDescripcion()).isEqualTo("Categoria JUnit Modificado");
		Assertions.assertThat(resp.getBody().getIdCategoria()).isEqualTo(id);
		
		
	}
	
	@Test
	@Order(4)
	public void pruebaUpdateParcialCategoria() {
		
		ResponseEntity<Categoria> resp = controller.updateCategoryDescripcion(id, "Categoria Junit Parcial");
		
		Assertions.assertThat(resp.getBody()).isNotEqualTo(null);
		Assertions.assertThat(resp.getBody().getCategoriaDescripcion()).isEqualTo("Categoria Junit Parcial");
		Assertions.assertThat(resp.getBody().getIdCategoria()).isEqualTo(id);
		
		
	}
	
	@Test
	@Order(5)
	
	public void pruebaDeleteCategoria() {
		
		ResponseEntity<Object> resp = controller.delete(id);
		ResponseEntity<Categoria> busqCat= controller.findById(id);
		
		Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		Assertions.assertThat(busqCat.getBody()).isEqualTo(null);
	}
	
	

}
