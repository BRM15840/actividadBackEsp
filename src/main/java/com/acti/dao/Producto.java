package com.acti.dao;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.boot.context.properties.ConstructorBinding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Producto {
	@Id
	@Column(name = "id_producto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value="Identificador del producto")
	private Long idProducto;
	
	
	@Column(name = "producto_descripcion")
	@ApiModelProperty(value="Descipcion del producto")
	private String productoDescripcion;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_categoria")
	@ApiModelProperty(value="Categoria del producto")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Categoria categoria;
	
	/*Campos de control*/
	@ApiModelProperty(value="Usuario que genera el producto")
	@Column(name = "usuario_creacion")
	private String usuarioCreacion;
	@ApiModelProperty(value="Fecha de creacion del producto")
	@Column(name = "fecha_creacion")
	private Timestamp fechaCrecion;
	@ApiModelProperty(value="Ultimo usuario que modifico el producto")
	@Column(name = "usuario_ultima_modificacion")
	private String usuarioUltModificacion;
	@ApiModelProperty(value="Fecha de la ultima modificacion realizada al producto")
	@Column(name = "fecha_ultima_modificacion")
	private Timestamp fechaUltModificacion;
	@ApiModelProperty(value="Nuemero de tarea que genero el producto")
	@Column(name = "numero_tarea")
	private String numeroTarea;
	@ApiModelProperty(value="Sucursal en la que esta disponible el producto")
	@Column(name = "sucursal")
	private String sucursal;
	

	
	
}
