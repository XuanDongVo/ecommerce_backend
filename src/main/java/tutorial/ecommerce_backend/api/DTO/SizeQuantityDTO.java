package tutorial.ecommerce_backend.api.DTO;

public class SizeQuantityDTO {
	private String size;
	private int quantity;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public SizeQuantityDTO(String size, int quantity) {
		super();
		this.size = size;
		this.quantity = quantity;
	}

	public SizeQuantityDTO() {
		super();
	}
	
}
