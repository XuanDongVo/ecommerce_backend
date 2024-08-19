package tutorial.ecommerce_backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.model.OrderDetail;
import tutorial.ecommerce_backend.model.WebOrder;

public interface DetailOrderDao extends JpaRepository<OrderDetail, Long> {
	List<OrderDetail> findByOrder(WebOrder order);
}
