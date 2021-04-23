package com.chooseme.proyect.controllersImpl;



import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chooseme.proyect.controllers.ProductsController;
import com.chooseme.proyect.dto.ProductsFilters;
import com.chooseme.proyect.entities.ProductToFront;
import com.chooseme.proyect.entities.Products;
import com.chooseme.proyect.service.ProductsService;

import utils.Exceptions.ApiUnprocessableEntity;


@RestController
public class ProductsControllersImpl implements ProductsController {
	
	@Autowired
	ProductsService productService;
	Products product;
	@Override
	@RequestMapping(value = "/products/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Products> getProductByName(@RequestBody ProductsFilters filter) {
		
		
		
		
		if(!(filter.getCategory() == null)) {
			return productService.findProductByCategory(filter);
		}
		
		if(!(filter.getName() == null)) {
			return productService.findUserByPName(filter);
		}
		//query = EntityManager.createQuery()
		//return productService.findUserByPName(filter);
		return null;
	}
	

	/*@Override
	@PostMapping(value = "/products/add",  produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addProducts(@RequestBody Products newproduct) {
		/*his.userValidator.validator(newproduct);
		productService.saveProduct(newproduct);
		return true;
	}*/
	
	/*
	@Override
	@PostMapping(value = "/products/review",  produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addProducts(@RequestBody String review) {
		/*his.userValidator.validator(newproduct);
		productService.saveProduct(review);
		return true;
	}*/
	
	@Override
	@RequestMapping(value = "/products/test", method = RequestMethod.GET, produces = "application/json")
	public Boolean producttest() {
		return true;
	}
}
