package tutorial.ecommerce_backend.api.controller.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tutorial.ecommerce_backend.api.DTO.CategoryDTO;
import tutorial.ecommerce_backend.entity.Category;
import tutorial.ecommerce_backend.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}

	@PostMapping("/add")
	public void addCategory(@AuthenticationPrincipal @Valid @RequestBody CategoryDTO categoryDTO) {
		categoryService.addCategory(categoryDTO);
	}

	@PostMapping("/delete/{id}")
	public void deleteCategory(@AuthenticationPrincipal @PathVariable long id) {
		categoryService.deleteCategory(id);
	}
}
