package tutorial.ecommerce_backend.api.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.api.DTO.ProductDTO;
import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;


	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
		ProductDTO productDTO = productService.getProductById(id);
		if (productDTO != null) {
			return ResponseEntity.ok(productDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/subcategory")
	public ResponseEntity<?> getProductByCategory(@RequestParam("id") Long id,
			@RequestParam(value = "index", defaultValue = "0") int offset,
			@RequestParam(value = "sort", defaultValue = "name") String sortBy) {
		try {
			Sort sort = Sort.by(sortBy);
			Page<Product> products = productService.getProductBySubCategory(id, offset, sort);
			if (products != null && !products.isEmpty()) {
				return ResponseEntity.ok(products);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Products not found with categoryId " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving products: " + e.getMessage());
		}
	}

	@GetMapping("/gender/{gender}")
	public ResponseEntity getProductByGender(@PathVariable String gender,
			@RequestParam(value = "index", defaultValue = "0") int offset,
			@RequestParam(value = "sort", defaultValue = "name") String sortBy) {
		Sort sort = Sort.by(sortBy);
		Page<Product> products = productService.getProductsByGender(gender, offset, sort);
		if (products != null && !products.isEmpty()) {
			return ResponseEntity.ok(products);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("/random/{quantity}")
	public ResponseEntity<List<Product>> getRandomProduct(@PathVariable int quantity) {
		List<Product> products = productService.getRandomProducts(quantity);
		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	

}
