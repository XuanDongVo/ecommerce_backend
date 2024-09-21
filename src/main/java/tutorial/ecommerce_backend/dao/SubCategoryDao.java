package tutorial.ecommerce_backend.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.entity.SubCategory;

public interface SubCategoryDao extends JpaRepository<SubCategory, Long> {
	
	Optional<SubCategory> findByName(String name);

}
