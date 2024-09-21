package tutorial.ecommerce_backend.api.DTO;

import tutorial.ecommerce_backend.entity.Product;

public class CartDetailRequest {
	private Product product;
	private Integer quantity;
	private String size;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	// Getters and setters
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
