package tutorial.ecommerce_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.dao.DetailOrderDao;
import tutorial.ecommerce_backend.dao.WebOrderDao;
import tutorial.ecommerce_backend.entity.OrderDetail;
import tutorial.ecommerce_backend.entity.WebOrder;

@Service
public class DetailOrderService {

	@Autowired
	private DetailOrderDao detailOrderDao;
	@Autowired
	private WebOrderDao webOrderDao;

	public List<OrderDetail> getOrderDetailByOrderId(Long id) {
		WebOrder order = webOrderDao.findById(id)
				.orElseThrow(() -> new RuntimeException("Can not find order by id: " + id));

		List<OrderDetail> orderDetails = detailOrderDao.findByOrder(order);
		return orderDetails;
	}
}
