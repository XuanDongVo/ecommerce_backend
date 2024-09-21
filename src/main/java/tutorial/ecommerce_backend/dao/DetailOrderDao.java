package tutorial.ecommerce_backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.entity.OrderDetail;
import tutorial.ecommerce_backend.entity.WebOrder;

public interface DetailOrderDao extends JpaRepository<OrderDetail, Long> {
	List<OrderDetail> findByOrder(WebOrder order);
}
