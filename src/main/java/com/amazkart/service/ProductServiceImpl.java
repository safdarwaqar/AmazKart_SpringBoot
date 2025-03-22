package com.amazkart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.amazkart.dto.ProductDTO;
import com.amazkart.entity.Category;
import com.amazkart.entity.Product;
import com.amazkart.repository.CategoryRepository;
import com.amazkart.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository; // Assuming a repository exists

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {

		Category categoryFromDb = categoryRepository.findById(productDTO.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid category id:" + productDTO.getCategoryId()));

		Product product = new Product();
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setStock(productDTO.getStock());
		product.setCategory(categoryFromDb); // Assuming Category has a matching
												// constructor

		Product savedProduct = productRepository.save(product);
		return mapToDTO(savedProduct);
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		return mapToDTO(product);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setStock(productDTO.getStock());
		product.setCategory(Category.builder().name("Electronics").build()); // Update category

		Product updatedProduct = productRepository.save(product);
		return mapToDTO(updatedProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		productRepository.delete(product);
	}

	private ProductDTO mapToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setStock(product.getStock());
		productDTO.setCategoryId(product.getCategory().getId());
		return productDTO;
	}

}
