package com.acti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acti.dao.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
