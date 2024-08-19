package tutorial.ecommerce_backend.api.DTO;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
	@NotBlank
	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
