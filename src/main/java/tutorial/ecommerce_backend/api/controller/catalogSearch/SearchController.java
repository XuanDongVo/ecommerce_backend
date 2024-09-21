package tutorial.ecommerce_backend.api.controller.catalogSearch;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.service.ProductService;

@RestController
@RequestMapping("catalogsearch")
public class SearchController {
	@Autowired
	private ProductService productService;

	@GetMapping("/result")
	public ResponseEntity<Page<Product>> searchProducts(@RequestParam("search") String search,
			   @RequestParam(value = "index", defaultValue = "0")int offset, @RequestParam(value = "sort", defaultValue = "name") String sortBy) {
		Sort sort = Sort.by(sortBy);
		Page<Product> products = productService.searchProduct(search, offset, sort);
		if (products.hasContent()) {
			return ResponseEntity.ok(products);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
