package tutorial.ecommerce_backend.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.admin.dto.AdminProductDto;
import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.service.ProductService;

@RestController
@RequestMapping("/admin/product")
public class AdProductController {
	@Autowired
	private ProductService productService;
	
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public List<Product> getProducts() {
		return productService.getProducts();
	}

	@PostMapping("/add")
	public void addProduct( @AuthenticationPrincipal @RequestBody AdminProductDto productDto) {
		productService.addProduct(productDto);
	}

	@DeleteMapping("delete/{id}")
	public void removeProduct(@AuthenticationPrincipal @PathVariable Long id) {
		productService.removeProduct(id);
	}

	@PostMapping("/edit")
	public void modifyProduct(@AuthenticationPrincipal @RequestBody AdminProductDto productDTO) {
		productService.modifyProduct(productDTO);
	}
}
