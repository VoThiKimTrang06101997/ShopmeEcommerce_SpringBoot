package com.trang.ShopmeBackEnd_Admin.service.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.trang.ShopmeBackEnd_Admin.PageInfo.CategoryPageInfo;
import com.trang.ShopmeBackEnd_Admin.repository.CategoryRepository;
import com.trang.ShopmeCommon.entity.Category;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {
	public static final int ROOT_CATEGORIES_PER_PAGE = 4;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> listByPage(CategoryPageInfo categoryPageInfo, int pageNum, String sortDir, String keyword) {
		// return (List<Category>) categoryRepository.findAll();

		Sort sort = Sort.by("name");

		if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

		Page<Category> pageCategories = null;

		if (keyword != null && !keyword.isEmpty()) {
			pageCategories = categoryRepository.search(keyword, pageable);
		} else {
			pageCategories = categoryRepository.findRootCategories(pageable);
		}

		// Page<Category> pageCategories =
		// categoryRepository.findRootCategories(pageable);
		List<Category> rootCategories = pageCategories.getContent();

		categoryPageInfo.setTotalElements(pageCategories.getTotalElements());
		categoryPageInfo.setTotalPages(pageCategories.getTotalPages());

		if (keyword != null && !keyword.isEmpty()) {
			List<Category> searchResult = pageCategories.getContent();
			for (Category category : searchResult) {
				category.setHasChildren(category.getChildren().size() > 0);
			}
			return searchResult;
		} else {
			return listHierachicalCategories(rootCategories, sortDir);
		}
	}

	private List<Category> listHierachicalCategories(List<Category> rootCategories, String sortDir) {
		List<Category> hierachicalCategories = new ArrayList<>();

		for (Category rootCategory : rootCategories) {
			hierachicalCategories.add(Category.copyFull(rootCategory));

			Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);

			for (Category subCategory : children) {
				String name = "--" + subCategory.getName();
				hierachicalCategories.add(Category.copyFull(subCategory, name));

				listSubHierarchicalCategories(hierachicalCategories, subCategory, 1, sortDir);
			}
		}

		return hierachicalCategories;
	}

	private void listSubHierarchicalCategories(List<Category> hierachicalCategories, Category parent, int subLevel,
			String sortDir) {
		Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);

		int newSubLevel = subLevel + 1;

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			hierachicalCategories.add(Category.copyFull(subCategory, name));

			listSubHierarchicalCategories(hierachicalCategories, subCategory, newSubLevel, sortDir);
		}
	}

	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	public List<Category> listCategoriesUsedInForm() {
		List<Category> categoriesUsedInForm = new ArrayList<>();

		Iterable<Category> categoriesInDB = categoryRepository.findRootCategories(Sort.by("name").ascending());

		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesUsedInForm.add(Category.copyIdAndName(category));

				Set<Category> children = sortSubCategories(category.getChildren());

				for (Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

					listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
				}
			}
		}

		return categoriesUsedInForm;
	}

	private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = sortSubCategories(parent.getChildren());

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}

			name += subCategory.getName();

			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

			listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
		}
	}

	public Category get(Integer id) throws CategoryNotFoundException {
		try {
			return categoryRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CategoryNotFoundException("Could not find any category with ID " + id);
		}

	}

	public String checkIsNameUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null || id == 0);

		var categoryByName = categoryRepository.findByName(name);

		if (isCreatingNew) {
			if (categoryByName != null) {
				return "DuplicatedName";
			} else {
				var categoryByAlias = categoryRepository.findByAlias(alias);
				if (categoryByAlias != null) {
					return "DuplicatedAlias";
				}
			}
		} else {
			if (categoryByName != null && categoryByName.getId() != id) {
				return "DuplicatedName";
			}
			var categoryByAlias = categoryRepository.findByAlias(alias);
			if (categoryByAlias != null && categoryByAlias.getId() != id) {
				return "DuplicatedAlias";
			}
		}

		return "OK";
	}

	private SortedSet<Category> sortSubCategories(Set<Category> children) {
		return sortSubCategories(children, "asc");
	}

	private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
		SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {

			@Override
			public int compare(Category category1, Category category2) {
				if (sortDir.equals("asc")) {
					return category1.getName().compareTo(category2.getName());
				} else {
					return category2.getName().compareTo(category1.getName());
				}
			}
		});

		sortedChildren.addAll(children);
		return sortedChildren;
	}

	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
		categoryRepository.updateEnabledStatus(id, enabled);
	}

	public void delete(Integer id) throws CategoryNotFoundException {
		Long countById = categoryRepository.countById(id);

		if (countById == null || countById == 0) {
			throw new CategoryNotFoundException("Could not find any category with ID: " + id);
		}

		categoryRepository.deleteById(id);
	}
}
