package tutorial.ecommerce_backend.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class EncryptionService {

	/*
	 * @Value("${encryption.salt.rounds}"): Tiêm giá trị của encryption.salt.rounds từ file cấu hình vào biến saltRounds.
	 */
	@Value("${encryption.salt.rounds}")
	private int saltRounds ;

	private String salt;

	/*
	 * @PostConstruct: Annotation dùng để đánh dấu một phương thức sẽ được gọi ngay sau khi bean được khởi tạo và các dependency được tiêm vào.
	 */
	@PostConstruct
	public void postContruct() {
		/*
		 * BCrypt.gensalt(saltRounds): Tạo salt dựa trên số vòng (rounds) được chỉ định.
		 * Salt là một chuỗi ngẫu nhiên được thêm vào mật khẩu trước khi băm để tăng độ bảo mật.
		 */
		salt = BCrypt.gensalt(saltRounds);
	}

	public String encryptPassword(String password) {
		/*
		 * Băm mật khẩu bằng cách sử dụng salt đã tạo ra ở phương thức postContruct.
		 */
		return BCrypt.hashpw(password, salt);
	}

	public boolean verifyPassword(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
}
