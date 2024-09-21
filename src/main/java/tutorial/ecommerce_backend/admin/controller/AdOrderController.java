package tutorial.ecommerce_backend.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.entity.WebOrder;
import tutorial.ecommerce_backend.service.WebOrderService;

@RestController
@RequestMapping("admin/order")
public class AdOrderController {

	@Autowired
	private WebOrderService orderService;

	@GetMapping
	public List<WebOrder> getOrders() {
		return orderService.getOrders();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> removeOrderById(@PathVariable Long id) {
		try {
			orderService.removeOrderById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/count")
	public ResponseEntity<?> countAllOrder() {
		try {
			Long count = orderService.countAllOrder();
			return ResponseEntity.ok(count);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}


}
