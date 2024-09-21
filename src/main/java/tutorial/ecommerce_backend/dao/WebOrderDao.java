package tutorial.ecommerce_backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.entity.LocalUser;
import tutorial.ecommerce_backend.entity.WebOrder;

public interface WebOrderDao extends JpaRepository<WebOrder, Long> {
	List<WebOrder> findByUser(LocalUser user);
	 long count();
}
