package tutorial.ecommerce_backend.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SizeDTO {
	@NotBlank
	@NotNull
	private String size;
	@NotNull
	@NotBlank
	private String type;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
