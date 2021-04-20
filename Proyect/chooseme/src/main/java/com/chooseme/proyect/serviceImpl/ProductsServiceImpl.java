package com.chooseme.proyect.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chooseme.proyect.dto.ProductsFilters;
import com.chooseme.proyect.entities.Products;
import com.chooseme.proyect.repository.ProductsRepository;
import com.chooseme.proyect.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {
	@Autowired
	ProductsRepository productRepository;
	Iterable<Products> nameproductcheck;
	Products product;
	@Override
	public Iterable<Products> findUserByPName(ProductsFilters filter) {
		
		nameproductcheck = null;
		String name = filter.getName();
						
		nameproductcheck =  productRepository.getProductByProductname(name);
		return nameproductcheck;
	}

	@Override
	public Products saveProduct(Products newproduct) {
		return productRepository.save(newproduct);
	}
	
	
	
}
