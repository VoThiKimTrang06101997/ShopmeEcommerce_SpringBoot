package com.trang.ShopmeBackEnd_Admin.service.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trang.ShopmeBackEnd_Admin.repository.ProductRepository;
import com.trang.ShopmeCommon.entity.Brand;
import com.trang.ShopmeCommon.entity.Product;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 5;
	
	@Autowired
	private ProductRepository productRepository;

	public List<Product> listAll() {
		return (List<Product>) productRepository.findAll();
	}
	
	public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword, Integer categoryId) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);

		if (keyword != null && !keyword.isEmpty()) {
			if(categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				return productRepository.findAllInCategory(categoryId, categoryIdMatch, pageable);
			}
			
			return productRepository.findAll(keyword, pageable);
		}
		
		if(categoryId != null && categoryId > 0) {
			String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
			return productRepository.findAllInCategory(categoryId, categoryIdMatch, pageable);
		}

		return productRepository.findAll(pageable);
	}
	
//	public void searchProducts(int pageNum, ) {
//		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
//		String keyword = helper.getKeyword();		
//		Page<Product> page = productRepository.searchProductsByName(keyword, pageable);		
//		helper.updateModelAttributes(pageNum, page);
//	}


	public Product save(Product product) {
		if (product.getId() == null) {
			product.setCreatedTime(new Date());
		}

		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		} else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}
		product.setUpdatedTime(new Date());

		return productRepository.save(product);
	}
	
	public void saveProductPrice(Product productInForm) {
		Product productInDB = productRepository.findById(productInForm.getId()).get();
		productInDB.setCost(productInForm.getCost());
		productInDB.setPrice(productInForm.getPrice());
		productInDB.setDiscountPercent(productInForm.getDiscountPercent());
		
		productRepository.save(productInDB);
	}

	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = productRepository.findByName(name);

		if (isCreatingNew) {
			if (productByName != null)
				return "Duplicate";
		} else {
			if (productByName != null && productByName.getId() != null) {
				return "Duplicate";
			}
		}
		return "OK";
	}

	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		productRepository.updateEnabledStatus(id, enabled);
	}

	public void deleteProduct(Integer id) throws ProductNotFoundException {
		Long countById = productRepository.countById(id);

		if (countById == null || countById == 0) {
			throw new ProductNotFoundException("Could not find any product with ID: " + id);
		}
		productRepository.deleteById(id);
	}
	
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return productRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
		
	}

}
