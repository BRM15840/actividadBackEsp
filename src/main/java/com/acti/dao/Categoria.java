package com.acti.dao;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.context.properties.ConstructorBinding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categoria")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {
	@Id
	@Column(name = "id_categoria")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value="Identificador de la categoria")
	private Long idCategoria;
	
	@Column(name = "categoria_descripcion")
	@ApiModelProperty(value="Descripcion de la categoria")
	private String categoriaDescripcion;
	

	/*Campos de control*/
	@Column(name = "usuario_creacion")
	@ApiModelProperty(value="Usuario que genera la categoria")
	private String usuarioCreacion;
	@ApiModelProperty(value="Fecha de creacion de la categoria")
	@Column(name = "fecha_creacion")
	private Timestamp fechaCrecion;
	@ApiModelProperty(value="Ultimo usuario que modifico la categoria")
	@Column(name = "usuario_ultima_modificacion")
	private String usuarioUltModificacion;
	@ApiModelProperty(value="Fecha de modificacion de la categoria")
	@Column(name = "fecha_ultima_modificacion")
	private Timestamp fechaUltModificacion;
	@ApiModelProperty(value="Nuemero de tarea que genero la categoria")
	@Column(name = "numero_tarea")
	private String numeroTarea;
	@ApiModelProperty(value="Sucursal en la que esta disponible la categoria")
	@Column(name = "sucursal")
	private String sucursal;
	

	
}
