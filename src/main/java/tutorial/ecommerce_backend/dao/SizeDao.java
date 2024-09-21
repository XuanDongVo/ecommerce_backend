package tutorial.ecommerce_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.entity.Size;

public interface SizeDao extends JpaRepository<Size, Long> {
	public Optional<Size> findBySize(String size);
	public List<Size> findByType(String type);
}
