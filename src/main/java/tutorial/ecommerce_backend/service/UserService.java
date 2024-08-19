package tutorial.ecommerce_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.api.DTO.LoginBody;
import tutorial.ecommerce_backend.api.DTO.RegistrationBody;
import tutorial.ecommerce_backend.dao.CartDao;
import tutorial.ecommerce_backend.dao.LocalUserDao;
import tutorial.ecommerce_backend.dao.RoleDao;
import tutorial.ecommerce_backend.exception.UserException;
import tutorial.ecommerce_backend.model.Cart;
import tutorial.ecommerce_backend.model.LocalUser;
import tutorial.ecommerce_backend.security.service.EncryptionService;
import tutorial.ecommerce_backend.security.service.JWTService;

@Service
public class UserService {

	@Autowired
	private LocalUserDao userDao;
	@Autowired
	private EncryptionService encryptionService;
	@Autowired
	private JWTService jwtService;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private CartDao cartDao;

	public void registerUser(RegistrationBody registrationBody) throws UserException {
		if (userDao.findByEmail(registrationBody.getEmail()).isPresent()
				|| userDao.findByUsername(registrationBody.getUsername()).isPresent()) {
			throw new UserException();
		}
		LocalUser user = new LocalUser();
		user.setUsername(registrationBody.getUsername());
		user.setEmail(registrationBody.getEmail());
		user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
		  user.setRole(roleDao.findByName("role_user"));
		userDao.save(user);

		Optional<LocalUser> opUser = userDao.findByUsername(registrationBody.getUsername());
		if (opUser.isPresent()) {
			Cart cart = new Cart();
			cart.setUser(opUser.get()); // Set the user in the cart
			cartDao.save(cart); // Save the cart
		}
	}

	public String loginUser(LoginBody loginBody) {
		System.out.println(loginBody);
		
		Optional<LocalUser> opUserByEmail = userDao.findByEmail(loginBody.getEmailOrUserName());
		Optional<LocalUser> opUserByName = userDao.findByUsername(loginBody.getEmailOrUserName());
		LocalUser user = null;
		if (opUserByEmail.isPresent()) {
			user = opUserByEmail.get();
		} else if (opUserByName.isPresent()) {
			user = opUserByName.get();
		} else {
			throw new UsernameNotFoundException(
					"User not found with email or username: " + loginBody.getEmailOrUserName());
		}
		// kiem tra xem co trung mat khau khong
		if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
			// ma hoa
			return jwtService.generateJWT(user);
		}
		return null;
	}
}
