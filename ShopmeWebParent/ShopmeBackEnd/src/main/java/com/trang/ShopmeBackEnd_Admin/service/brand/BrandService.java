package com.trang.ShopmeBackEnd_Admin.service.brand;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trang.ShopmeBackEnd_Admin.repository.BrandRepository;
import com.trang.ShopmeCommon.entity.Brand;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BrandService {
	
	public static final int BRANDS_PER_PAGE = 10;
	
	@Autowired
	private BrandRepository brandRepository;

	public List<Brand> listAll() {
		return (List<Brand>) brandRepository.findAll();
	}

	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	public Page<Brand> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE, sort);

		if (keyword != null) {
			return brandRepository.findAll(keyword, pageable);
		}

		return brandRepository.findAll(pageable);
	}

	public Brand get(Integer id) throws BrandNotFoundException {
		try {
			return brandRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new BrandNotFoundException("Could not find any brand with ID " + id);
		}
	}

	public Brand editBrand(Brand brandInForm) {
		Brand brandInDB = brandRepository.findById(brandInForm.getId()).get();

		if (brandInForm.getLogo() != null) {
			brandInDB.setLogo(brandInForm.getLogo());
		}

		brandInDB.setName(brandInForm.getName());

		return brandRepository.save(brandInDB);
	}

	public void delete(Integer id) throws BrandNotFoundException {
		var brandId = brandRepository.countById(id);

		if (brandId == null || brandId == 0) {
			throw new BrandNotFoundException("This Brand with ID:" + id + "dont exist");
		}
		brandRepository.deleteById(id);
	}

	public String checkUniqueBrand(Integer id, String name) {
		boolean isCreatingNew = id == null || id == 0;

		var brandByName = brandRepository.findByName(name);

		if (isCreatingNew) {
			if (brandByName != null) {
				return "duplicatedBrand";
			}
		} else {
			if (brandByName != null && brandByName.getId() != null) {
				return "duplicatedBrand";
			}
		}

		return "OK";

	}

}
