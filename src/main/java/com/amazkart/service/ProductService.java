package com.amazkart.service;

import java.util.List;

import com.amazkart.dto.ProductDTO;

public interface ProductService {
	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO getProductById(Long id);

	List<ProductDTO> getAllProducts();

	ProductDTO updateProduct(Long id, ProductDTO productDTO);

	void deleteProduct(Long id);
}
