package tutorial.ecommerce_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.api.DTO.CategoryDTO;
import tutorial.ecommerce_backend.dao.CategoryDao;
import tutorial.ecommerce_backend.entity.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao categoryDao;

	public List<Category> getCategories() {
		return categoryDao.findAll();
	}

	public void addCategory(  CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		categoryDao.save(category);
	}

	public void deleteCategory(long id) {
		Optional<Category> opCategory = categoryDao.findById(id);
		if (opCategory.isPresent()) {
			categoryDao.delete(opCategory.get());
		}
	}

}
