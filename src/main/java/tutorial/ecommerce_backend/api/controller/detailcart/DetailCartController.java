package tutorial.ecommerce_backend.api.controller.detailcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.api.DTO.CartDetailRequest;
import tutorial.ecommerce_backend.api.DTO.DetailCartDto;
import tutorial.ecommerce_backend.entity.DetailCart;
import tutorial.ecommerce_backend.service.DetailCartService;

@RestController
@RequestMapping("/detailcart")
public class DetailCartController {
	@Autowired
	private DetailCartService cartService;

	@GetMapping
	public ResponseEntity<List<DetailCartDto>> getDetailCarts() {
		try {
			List<DetailCartDto> detailCarts = cartService.getDetailCarts();
			return ResponseEntity.ok(detailCarts);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> addDetailCart(@RequestBody CartDetailRequest request,
			@RequestHeader("Authorization") String token) {
		try {
			String jwtToken = token.substring(7);
			cartService.addDetailCart(request.getProduct(), request.getQuantity(), jwtToken, request.getSize());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add detail cart: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> removeDetailCart(@PathVariable Long id) {
		try {
			cartService.removeDetailCart(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to remove detail cart: " + e.getMessage());
		}
	}

	// lay ra so luong san pham trong gio hang
	@GetMapping("/totalQuantity")
	public ResponseEntity getTotalQuantityInCart(@RequestHeader("Authorization") String token) {
		try {
			String jwtToken = token.substring(7);
			int quantity = cartService.getTotalQuantityInCart(jwtToken);
			if(quantity < 0) {
				quantity = 0;
			}
			return ResponseEntity.ok(quantity);
		} catch (Exception e) {
			 return ResponseEntity.ok(0);
		}
	}

	@GetMapping("/detail")
	public ResponseEntity<Page<DetailCartDto>> getDetailCartForUser(@RequestHeader("Authorization") String token,
	        @RequestParam(value = "index", defaultValue = "0") int offset) {
		if(token  == null) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	    try {
	        String jwtToken = token.substring(7);
	        Page<DetailCartDto> details = cartService.getDetailForUser(jwtToken, offset, null);
	        if (details == null || details.isEmpty()) {
	        	return ResponseEntity.ok(Page.empty()); // Return an empty page with HTTP 200 OK
	        }
	        return ResponseEntity.ok(details);
	    } catch (Exception e) {
	        e.printStackTrace(); // Log lỗi để kiểm tra nguyên nhân
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@PostMapping("/update/{productName}")
	public ResponseEntity<?> updateQuantity(@RequestHeader("Authorization") String token, @PathVariable String productName , @RequestParam("quantity") int quantity , @RequestParam("size") String size){
		
		try {
			 String jwtToken = token.substring(7);
			 cartService.updateQuantity(productName, quantity, jwtToken,size);
			 return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
