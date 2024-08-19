package tutorial.ecommerce_backend.api.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.api.DTO.DetailCartDto;
import tutorial.ecommerce_backend.service.WebOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private WebOrderService orderService;

	// xử lí khi người dùng chọn hết sản phẩm
	@PostMapping("/{totalPrice}")
	public ResponseEntity<?> orderAllProduct(@RequestHeader("Authorization") String token,
			@PathVariable double totalPrice) {
		try {
			// Kiểm tra và tách phần "Bearer " khỏi token
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7);

				// Gọi dịch vụ để tạo đơn hàng và lấy danh sách lỗi
				List<String> errors = orderService.processOrderDetails(jwtToken, totalPrice);

				if (errors.isEmpty()) {
					// Trả về ResponseEntity với mã trạng thái HTTP 201 Created nếu thành công
					return ResponseEntity.status(HttpStatus.CREATED).build();
					
				} else {
					// Trả về ResponseEntity với mã trạng thái HTTP 400 Bad Request và danh sách lỗi
					// nếu có
					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
				}
				
			} else {
				// Trả về ResponseEntity với mã trạng thái HTTP 400 Bad Request nếu token không
				// hợp lệ
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format");
			}
		} catch (Exception e) {
			// Trả về ResponseEntity với mã trạng thái HTTP 500 Internal Server Error nếu có
			// lỗi xảy ra
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the order");
		}
	}

	// xử lí khi người dùng chỉ chọn vài sản phẩm để mua trong cartdetail
	@PostMapping("/some/{totalPrice}")
	public ResponseEntity<?> orderSomeProduct(@RequestHeader("Authorization") String token,
			@PathVariable double totalPrice, @RequestBody List<DetailCartDto> detailCart) {
		try {
			// Kiểm tra và tách phần "Bearer " khỏi token
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7);

				// Gọi dịch vụ để tạo đơn hàng và lấy danh sách lỗi
				List<String> errors = orderService.processOrderDetail(jwtToken, totalPrice, detailCart);
				
				if (errors.isEmpty()) {
					// Trả về ResponseEntity với mã trạng thái HTTP 201 Created nếu thành công
					return ResponseEntity.status(HttpStatus.CREATED).build();
				} else {
					// Trả về ResponseEntity với mã trạng thái HTTP 400 Bad Request và danh sách lỗi
					// nếu có
					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
				}
				
			} else {
				// Trả về ResponseEntity với mã trạng thái HTTP 400 Bad Request nếu token không
				// hợp lệ
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format");
			}
		} catch (Exception e) {
			// Trả về ResponseEntity với mã trạng thái HTTP 500 Internal Server Error nếu có
			// lỗi xảy ra
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the order");
		}
	}

}