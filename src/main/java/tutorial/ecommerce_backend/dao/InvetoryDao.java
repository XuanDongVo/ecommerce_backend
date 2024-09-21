package tutorial.ecommerce_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tutorial.ecommerce_backend.entity.Inventory;
import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.entity.Size;

public interface InvetoryDao extends JpaRepository<Inventory, Long> {
	List<Inventory> findByProduct(Product product);

	@Modifying
	@Query(value = "update inventory i set i.quantity = quantity -:quantity where i.product_id = :product_id and i.size_id = :size_id and i.quantity >= :quantity", nativeQuery = true)
	int decreaseStock(int quantity, Long product_id, long size_id);

	Optional<Inventory> findByProductAndSize(Product product, Size size);
}
