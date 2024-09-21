package tutorial.ecommerce_backend.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.entity.OrderDetail;
import tutorial.ecommerce_backend.service.DetailOrderService;


@RestController
@RequestMapping("/admin/orderDetail")
public class AdDetailOrderController {
	@Autowired
	private DetailOrderService detailOrderService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable Long id) {
		try {
			List<OrderDetail> orderDetails = detailOrderService.getOrderDetailByOrderId(id);
			return ResponseEntity.ok(orderDetails);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
