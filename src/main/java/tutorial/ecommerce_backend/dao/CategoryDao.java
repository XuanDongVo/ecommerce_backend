package tutorial.ecommerce_backend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
	public Optional<Category> findByName(String name);
}
