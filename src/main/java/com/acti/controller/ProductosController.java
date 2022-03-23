package com.acti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.acti.dao.*;
import com.acti.servicios.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = "http://localhost:8086/")
@RequestMapping(value="/producto")
public class ProductosController {
	
	@Autowired
	private IProductService prodServ;
	
	@GetMapping
	@ApiOperation(value="Regresa el listado de productos disponibles")
	public ResponseEntity<List<Producto>> findAll(){
		
		List<Producto> res = prodServ.listAllProducts();
		if(res.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(res);
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value = "Regresa el producto que concuerde con el id enviado",notes = "El id debe ser enviado a traves de la url, "
			+ "en caso de no encontrarse el producto deseado no se regresara contenido")
	public ResponseEntity<Producto> findById(@PathVariable(name="id",required = true) Long id){
		
		Producto prod = prodServ.getProduct(id);
		if(prod == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(prod);
	}
	
	@PostMapping
	@ApiOperation(value="Registra un nuevo producto",notes="El campo de id no debe de ser enviado,tambien "
			+ "es obligatoria enviar la categoria a la que pertenece el producto.")
	public ResponseEntity<Producto> createProducto(@RequestBody(required = true) @Valid Producto prodBody,BindingResult res){
		
		if(res!=null && res.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,formatMessage(res));
		}
		
		prodBody = prodServ.createProduct(prodBody);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(prodBody);
	}
	
	@PutMapping(value="/{id}")
	@ApiOperation(value="Actualiza el producto",notes="En caso de no enviar un id valido se regresara un status code de 404")
	public ResponseEntity<Producto> updateProduto(@PathVariable(name="id",required = true) Long id,
			@RequestBody(required = true)  Producto prodBody){
		
		Producto prodBD = prodServ.getProduct(id);
		if(prodBD == null) {
			return ResponseEntity.notFound().build();
			
		}
		prodBody.setIdProducto(id);
		prodBD =  prodServ.updateProduct(prodBody);
		
		return ResponseEntity.ok(prodBD);
	}
	
	@PatchMapping(value="{id}")
	@ApiOperation(value="Actualiza de forma parcial un producto",notes="Solo se actualiza la descripcion del producto")
	public ResponseEntity<Producto> updateProductoDescripcion(@PathVariable(name="id",required = true) Long id,
			@RequestParam(name="description", required = true) String description){
		
		Producto prodBD = prodServ.getProduct(id);
		if(prodBD == null) {
			return ResponseEntity.notFound().build();
			
		}
		prodBD =  prodServ.updateProducDescripcion(id,description);
		
		return ResponseEntity.ok(prodBD);
	}
	
	@DeleteMapping(value="/{id}")
	@ApiOperation(value="Elimina un producto",notes="El producto eliminado no puede ser recuperado")
	public ResponseEntity<Object> delete(@PathVariable(name="id",required = true) Long id){
		
		Producto prodBD = prodServ.getProduct(id);
		if(prodBD == null) {
			return ResponseEntity.notFound().build();
			
		}
		prodServ.deleteProduct(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	private String formatMessage(BindingResult bin) {
		List<Map<String,String>> mejs = bin.getFieldErrors().stream()
		.map(err ->{
			
			Map<String,String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		
		ErrorMessage errorMensaje =  ErrorMessage.builder()
				.code("01").mensajes(mejs).build();
		
		ObjectMapper mapper = new ObjectMapper();
		String JsonString = "";
		
		try {
			JsonString = mapper.writeValueAsString(errorMensaje);
		}catch(JsonProcessingException e) {
			e.printStackTrace();
			
		}
		return JsonString;
		
	}
	
	
	

}
