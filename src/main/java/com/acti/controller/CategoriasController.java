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

import com.acti.dao.Categoria;
import com.acti.servicios.ICategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = "http://localhost:8086/")
@RequestMapping(value="/categoria")
public class CategoriasController {
	@Autowired
	private ICategoryService catServ;
	
	@GetMapping
	@ApiOperation(value="Regresa el listado de categorias")
	public ResponseEntity<List<Categoria>> findAll(){
		
		List<Categoria> res = catServ.listAllCategorias();
		if(res.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(res);
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value = "Regresa la categoria que concuerde con el id enviado",notes = "El id debe ser enviado a traves de la url, "
			+ "en caso de no encontrarse la categoria deseado no se regresara contenido")
	public ResponseEntity<Categoria> findById(@PathVariable(name="id",required = true) Long id){
		
		Categoria cat = catServ.getCategoria(id);
		if(cat == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(cat);
	}
	
	@PostMapping
	@ApiOperation(value="Registra una nueva categoria",notes="El campo de id no debe de ser enviado ")	
	public ResponseEntity<Categoria> createCategory(@RequestBody(required = true) @Valid Categoria catBody,BindingResult res){
		
		if(res!= null && res.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,formatMessage(res));
		}
		
		catBody = catServ.createCategoria(catBody);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(catBody);
	}
	
	@PutMapping(value="/{id}")
	@ApiOperation(value="Actualiza la categoria",notes="En caso de no enviar un id valido se regresara un status code de 404")
	public ResponseEntity<Categoria> updateCategory(@PathVariable(name="id",required = true) Long id,
			@RequestBody(required = true)  Categoria catBody){
		
		Categoria catBD = catServ.getCategoria(id);
		if(catBD == null) {
			return ResponseEntity.notFound().build();
			
		}
		catBody.setIdCategoria(id);
		catBD =  catServ.updateCtaegoria(catBody);
		
		return ResponseEntity.ok(catBD);
	}
	
	@PatchMapping(value="{id}")
	@ApiOperation(value="Actualiza de forma parcial una categoria",notes="Solo se actualiza la descripcion de la categoria")
	public ResponseEntity<Categoria> updateCategoryDescripcion(@PathVariable(name="id",required = true) Long id,
			@RequestParam(name="description", required = true) String description){
		
		Categoria catBD = catServ.getCategoria(id);
		if(catBD == null) {
			return ResponseEntity.notFound().build();
			
		}
		catBD =  catServ.updateCategoriaDescripcion(id,description);
		
		return ResponseEntity.ok(catBD);
	}
	
	@DeleteMapping(value="/{id}")
	@ApiOperation(value="Elimina una categoria",notes="La categoria eliminada no puede ser recuperada")
	public ResponseEntity<Object> delete(@PathVariable(name="id",required = true) Long id){
		
		Categoria catBD = catServ.getCategoria(id);
		if(catBD == null) {
			return ResponseEntity.notFound().build();
			
		}
		catServ.deleteCategoria(id);
		
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
