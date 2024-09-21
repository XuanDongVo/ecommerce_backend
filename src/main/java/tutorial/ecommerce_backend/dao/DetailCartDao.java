package tutorial.ecommerce_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tutorial.ecommerce_backend.entity.Cart;
import tutorial.ecommerce_backend.entity.DetailCart;
import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.entity.Size;

public interface DetailCartDao extends JpaRepository<DetailCart, Long> {
	  List<DetailCart> findByCart(Cart cart); // Renamed method
	Optional<DetailCart> findByProductAndCartAndSize(Product product , Cart cart, Size size);
	
	
	@Modifying
	@Query(value = "UPDATE detail_cart i SET i.quantity = i.quantity + :quantity WHERE i.product_id = :product_id AND i.cart_id = :cart_id", nativeQuery = true)
	int updateQuantity(@Param("product_id") Long productId, @Param("cart_id") Long cartId, @Param("quantity") int quantity);
	
	@Query(value = "SELECT SUM(quantity) FROM detail_cart where cart_id = :cart_id" , nativeQuery = true)
	int getTotalQuantityInCart(Long cart_id);
	
	@Query(value = "SELECT * FROM detail_cart WHERE cart_id = :cartId" , nativeQuery = true)
	Page<DetailCart> findAllProductByCart(Long cartId, Pageable page);
	
	

}