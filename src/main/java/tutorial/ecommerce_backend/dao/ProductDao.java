package tutorial.ecommerce_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.entity.SubCategory;

public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	public Optional<Product> findByName(String name);

	public Page<Product> findBySubCategory(SubCategory subCategory, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
	public Page<Product> searchProduct(String name, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.subCategory.category.name = :gender")
	public Page<Product> findByGender(String gender, Pageable pageable);
	
	@Query(value = "SELECT * FROM product WHERE id > FLOOR(RAND() * (SELECT MAX(id) FROM product)) LIMIT :quantity", nativeQuery = true)
	public List<Product> getRandomProduct(int quantity);

}
