package tutorial.ecommerce_backend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tutorial.ecommerce_backend.entity.LocalUser;

public interface LocalUserDao extends JpaRepository<LocalUser, Long>{
	
	Optional<LocalUser> findByUsername(String username);
	
	Optional<LocalUser> findByEmail(String email);
	
	Optional<LocalUser> findByEmailOrUsername(String email , String name);

}
