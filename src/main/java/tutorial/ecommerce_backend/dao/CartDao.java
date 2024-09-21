package tutorial.ecommerce_backend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.entity.Cart;
import tutorial.ecommerce_backend.entity.LocalUser;

public interface CartDao extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUser(LocalUser user);
}
