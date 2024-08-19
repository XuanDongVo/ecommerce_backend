package tutorial.ecommerce_backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.model.LocalUser;
import tutorial.ecommerce_backend.model.WebOrder;

public interface WebOrderDao extends JpaRepository<WebOrder, Long> {
	List<WebOrder> findByUser(LocalUser user);
	 long count();
}
