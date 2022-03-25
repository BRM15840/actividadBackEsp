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

import com.acti.controller.ProductosController;
import com.acti.dao.Categoria;
import com.acti.dao.Producto;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerTest {
	
	private final Long id = 2L;
	
	@Autowired 
	private ProductosController prodController;
	
	private MapBindingResult bind = new MapBindingResult(new HashMap<>(), "");
	
	@Test
	@Order(1)
	public void pruebaObtenerProductos(){
		ResponseEntity<List<Producto>> resp=prodController.findAll();
		List<Producto> listProd= resp.getBody();
		Assertions.assertThat(listProd).size().isEqualTo(2);
		Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}
	
	@Test
	@Order(2)
	public void pruebaAgregarProducto() {
		Producto prodPrueba = Producto.builder()
				.productoDescripcion("Pruebas JUnit")
				.usuarioCreacion("Victor")
				.usuarioUltModificacion("Test")
				.numeroTarea("3").sucursal("123")
				.categoria(Categoria.builder().idCategoria(2L).build()).build()
				;
		
		
		ResponseEntity<Producto> resp = prodController.createProducto(prodPrueba, null);
		System.out.println("El porducto creado es: " + resp.getBody().getIdProducto());

		Assertions.assertThat(resp.getBody().getProductoDescripcion()).isEqualTo("Pruebas JUnit");
		Assertions.assertThat(resp.getBody().getIdProducto()).isNotEqualTo(null);
		
		
	}
	@Test
	@Order(3)
	public void pruebaUpdateProducto() {
		Producto prodPrueba = Producto.builder()
				.idProducto(id)
				.productoDescripcion("Pruebas JUnit Modificado")
				.usuarioUltModificacion("Test Mod")
				.numeroTarea("3").sucursal("123")
				.categoria(Categoria.builder().idCategoria(3L).build()).build()
				;
		
		System.out.println("El id del producto es: "+ id);
		ResponseEntity<Producto> resp = prodController.updateProduto(id, prodPrueba);
		
		
		Assertions.assertThat(resp.getBody().getProductoDescripcion()).isEqualTo("Pruebas JUnit Modificado");
		Assertions.assertThat(resp.getBody().getIdProducto()).isEqualTo(id);
		
		
	}
	
	@Test
	@Order(4)
	public void pruebaUpdateParcialProducto() {
		
		ResponseEntity<Producto> resp = prodController.updateProductoDescripcion(id, "Test Junit Parcial");
		
		Assertions.assertThat(resp.getBody()).isNotEqualTo(null);
		Assertions.assertThat(resp.getBody().getProductoDescripcion()).isEqualTo("Test Junit Parcial");
		Assertions.assertThat(resp.getBody().getIdProducto()).isEqualTo(id);
		
		
	}
	
	
	@Test
	@Order(5)
	public void pruebaDeleteProducto() {
		
		ResponseEntity<Object> resp = prodController.delete(id);
		ResponseEntity<Producto> busqProd = prodController.findById(id);
		
		Assertions.assertThat(busqProd.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		Assertions.assertThat(resp.getBody()).isEqualTo(null);
	}
	
	

}
