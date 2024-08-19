package tutorial.ecommerce_backend.api.DTO;

import java.util.Map;

public class ProductDTO {
	private String name;
	private double price;
	private String pathImage;
	private String subCategoryName;
	private Map<String, Integer> sizeQuantities;

	public Map<String, Integer> getSizeQuantities() {
		return sizeQuantities;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public void setSizeQuantities(Map<String, Integer> sizeQuantities) {
		this.sizeQuantities = sizeQuantities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

}
