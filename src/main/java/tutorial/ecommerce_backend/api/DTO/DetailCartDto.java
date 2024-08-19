package tutorial.ecommerce_backend.api.DTO;

public class DetailCartDto {
	private Long id;

	private String name;
	private String pathImage;
	private double price;
	private int quantity;
	private String size;
	
	
	
	public DetailCartDto() {
		super();
	}

	public DetailCartDto(Long id, String name, String pathImage, double price, int quantity, String size) {
	    this.id = id;
	    this.name = name;
	    this.pathImage = pathImage;
	    this.price = price;
	    this.quantity = quantity;
	    this.size = size;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
