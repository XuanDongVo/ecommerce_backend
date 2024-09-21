package tutorial.ecommerce_backend.api.controller.subcategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.entity.SubCategory;
import tutorial.ecommerce_backend.service.SubCategoryService;

@RestController
@RequestMapping("/subcategory")
public class SubCategoryController {
	@Autowired
	private SubCategoryService subCategoryService;
	
	@GetMapping
	public List<SubCategory> getSubCategories(){
		return subCategoryService.getSubCategories();
	}

}
