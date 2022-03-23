package com.acti.servicios;

import java.util.List;

import com.acti.dao.*;

public interface IProductService {
	public List<Producto> listAllProducts();
	
	public Producto getProduct(Long Id);
	
	public Producto createProduct(Producto producto);
	
	public Producto updateProduct(Producto producto);
	
	public Producto deleteProduct(Long Id);

	public Producto updateProducDescripcion(Long Id, String description);
}
