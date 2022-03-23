package com.acti.servicios;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acti.dao.Producto;
import com.acti.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;


@Service
public class ProductService implements IProductService {
	
	@Autowired
	private ProductoRepository prodRep;

	@Override
	public List<Producto> listAllProducts() {
		
		return prodRep.findAll();
	}

	@Override
	public Producto getProduct(Long Id) {
		
		return prodRep.findById(Id).orElse(null);
	}

	@Override
	public Producto createProduct(Producto producto) {
		producto.setFechaCrecion(new Timestamp(new Date().getTime()));
		producto.setFechaUltModificacion(new Timestamp(new Date().getTime()));
		
		return prodRep.save(producto);
	}

	@Override
	public Producto updateProduct(Producto producto) {
		Producto prodBd = getProduct(producto.getIdProducto());
		if(prodBd == null) {
			
			return null;
		}
		prodBd.setCategoria(producto.getCategoria());
		prodBd.setFechaUltModificacion(new Timestamp(new Date().getTime()));
		prodBd.setSucursal(producto.getSucursal());
		prodBd.setProductoDescripcion(producto.getProductoDescripcion());

		return prodRep.save(prodBd);
	}

	@Override
	public Producto deleteProduct(Long Id) {
		Producto prodBd = getProduct(Id);
		if(prodBd == null) {
			return null;
		}
		prodRep.delete(prodBd);
		
		return prodBd;
	}

	@Override
	public Producto updateProducDescripcion(Long Id, String description) {
		Producto prodBd = getProduct(Id);
		if(prodBd == null) {
			
			return null;
		}
		prodBd.setFechaUltModificacion(new Timestamp(new Date().getTime()));
		prodBd.setProductoDescripcion(description);
		return prodRep.save(prodBd);
	}

}
