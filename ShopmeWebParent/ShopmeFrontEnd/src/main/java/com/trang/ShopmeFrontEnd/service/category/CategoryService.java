package com.trang.ShopmeFrontEnd.service.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trang.ShopmeCommon.entity.Category;
import com.trang.ShopmeFrontEnd.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> listNoChildrenCategories() {
		List<Category> listNoChildrenCategories = new ArrayList<>();

		List<Category> listEnabledCategories = categoryRepository.findAllEnabled();

		listEnabledCategories.forEach(category -> {
			Set<Category> children = category.getChildren();
			if (children == null || children.size() == 0) {
				listNoChildrenCategories.add(category);
			}
		});

		return listNoChildrenCategories;
	}

//	public List<Category> listNoChildrenCategories() {
//		List<Category> listEnabledCategories = categoryRepository.findAllEnabled();
//		List<Category> noChildrenCategories = listEnabledCategories.stream().filter(cat -> cat.isHasChildren() == false)
//				.collect(Collectors.toList());
//		return noChildrenCategories;
//	}

	public Category getCategory(String alias) throws CategoryNotFoundException {
		 Category category = categoryRepository.findByAliasEnabled(alias);
		 if(category == null) {
			 throw new CategoryNotFoundException("Could not find any categories with alias " + alias );
		 }
		
		return category;
	}

	public List<Category> getCategoryParents(Category child) {
		List<Category> listParents = new ArrayList<>();

		Category parent = child.getParent();

		while (parent != null) {
			listParents.add(0, parent);
			parent = parent.getParent();
		}

		listParents.add(child);

		return listParents;
	}

}
