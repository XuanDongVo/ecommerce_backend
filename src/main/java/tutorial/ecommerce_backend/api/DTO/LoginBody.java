package tutorial.ecommerce_backend.api.DTO;


import jakarta.validation.constraints.NotNull;

public class LoginBody {
	private String emailOrUserName;
	@NotNull
	private String password;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailOrUserName() {
		return emailOrUserName;
	}

	public void setEmailOrUserName(String emailOrUserName) {
		this.emailOrUserName = emailOrUserName;
	}
}
