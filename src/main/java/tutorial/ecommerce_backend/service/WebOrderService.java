package tutorial.ecommerce_backend.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tutorial.ecommerce_backend.api.DTO.DetailCartDto;
import tutorial.ecommerce_backend.dao.CartDao;
import tutorial.ecommerce_backend.dao.DetailCartDao;
import tutorial.ecommerce_backend.dao.DetailOrderDao;
import tutorial.ecommerce_backend.dao.InvetoryDao;
import tutorial.ecommerce_backend.dao.LocalUserDao;
import tutorial.ecommerce_backend.dao.ProductDao;
import tutorial.ecommerce_backend.dao.SizeDao;
import tutorial.ecommerce_backend.dao.WebOrderDao;
import tutorial.ecommerce_backend.entity.Cart;
import tutorial.ecommerce_backend.entity.DetailCart;
import tutorial.ecommerce_backend.entity.Inventory;
import tutorial.ecommerce_backend.entity.LocalUser;
import tutorial.ecommerce_backend.entity.OrderDetail;
import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.entity.Size;
import tutorial.ecommerce_backend.entity.WebOrder;
import tutorial.ecommerce_backend.jwt.JWTService;

@Service
public class WebOrderService {

	@Autowired
	private WebOrderDao orderDao;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private LocalUserDao userDao;

	@Autowired
	private CartDao cartDao;

	@Autowired
	private DetailCartDao detailCartDao;

	@Autowired
	private DetailOrderDao detailOrderDao;

	@Autowired
	private InvetoryDao inventoryDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private SizeDao sizeDao;
	
	public List<WebOrder> getOrders(){
		return orderDao.findAll();
	}

	// Tạo đơn hàng
	public WebOrder createOrder(String token, double price, LocalUser user) {
		WebOrder order = new WebOrder();
		order.setTotalCost(price);
		order.setUser(user);
		order.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		orderDao.save(order);
		return order;
	}

	private List<String> processOrderItems(WebOrder order, List<DetailCartDto> detailCarts, Cart cart) {
	    List<String> errors = new ArrayList<>();

	    for (DetailCartDto detailCartDto : detailCarts) {
	        try {
	            OrderDetail orderDetail = new OrderDetail();
	            orderDetail.setOrder(order);
	            Product product = productDao.findByName(detailCartDto.getName())
	                    .orElseThrow(() -> new RuntimeException("Product not found: " + detailCartDto.getName()));
	            
	            orderDetail.setProduct(product);
	            orderDetail.setQuantity(detailCartDto.getQuantity());
	            
	            Size size = sizeDao.findBySize(detailCartDto.getSize())
	                    .orElseThrow(() -> new RuntimeException("Size not found: " + detailCartDto.getSize()));
	            orderDetail.setSize(size);

	            detailOrderDao.save(orderDetail);

	            // Chỉnh lại số lượng trong kho hàng
	            int quantityInStock = inventoryDao.decreaseStock(detailCartDto.getQuantity(), product.getId(), size.getId());
	            if (quantityInStock == 0) { // Kiểm tra số lượng sau khi giảm
	                errors.add( product.getName() + " và size: " + size.getSize() );
	                continue; // Tiếp tục xử lý các mặt hàng khác
	            }

	            // Xóa chi tiết giỏ hàng sau khi đặt hàng
	            DetailCart detailCart = detailCartDao.findByProductAndCartAndSize(product, cart, size)
	                    .orElseThrow(() -> new RuntimeException("DetailCart not found for product: " + product.getName() + " and size: " + size.getSize()));
	            detailCartDao.delete(detailCart);
	        } catch (Exception e) {
	            errors.add("Error processing DetailCart ID: " + detailCartDto.getId() + ". Error: " + e.getMessage());
	        }
	    }
	    return errors;
	}

	@Transactional
	public List<String> processOrderDetails(String token, double price) {
	    // Lấy tên người dùng từ token
		 LocalUser user = jwtService.getUsernameByToken(token);

	    WebOrder order = createOrder(token, price, user);

	    // Lấy giỏ hàng của người dùng
	    Cart cart = cartDao.findByUser(user)
	            .orElseThrow(() -> new RuntimeException("Cart not found for user: " ));

	    // Lấy chi tiết giỏ hàng
	    List<DetailCart> detailCarts = detailCartDao.findByCart(cart);

	    // Chuyển đổi từ List<DetailCart> sang List<DetailCartDto>
	    List<DetailCartDto> detailCartDtos = detailCarts.stream()
	            .map(d -> new DetailCartDto(d.getId(), d.getProduct().getName(), d.getProduct().getPathImage(),
	                    d.getProduct().getPrice(), d.getQuantity(), d.getSize().getSize()))
	            .toList();

	    // Xử lý chi tiết đơn hàng và lấy danh sách lỗi
	    return processOrderItems(order, detailCartDtos, cart);
	}
	
	//xử lí khi người dùng chỉ chọn vài sản phẩm để mua trong cartdetail
	@Transactional
	public List<String> processOrderDetail(String token, double price, List<DetailCartDto> detailCarts) {
		 // Lấy tên người dùng từ token
		 LocalUser user = jwtService.getUsernameByToken(token);


		WebOrder order = createOrder(token, price, user);

		// Lấy giỏ hàng của người dùng
		Cart cart = cartDao.findByUser(user)
				.orElseThrow(() -> new RuntimeException("Cart not found for user: " + user.getUsername()));

		// Xử lý chi tiết đơn hàng
		return processOrderItems(order, detailCarts, cart);
	}
	
	
	//xóa đơn hàng 
	@Transactional
	public void removeOrderById(Long id) {
		List<OrderDetail> orderDetails = detailOrderDao.findByOrder(orderDao.findById(id).get());
		orderDetails.forEach(orderDetail -> {
			// lấy hàng trong kho 
			  Inventory inventory = inventoryDao.findByProductAndSize(orderDetail.getProduct(), orderDetail.getSize())
				        .orElseThrow(() -> new RuntimeException("Cannot find inventory by product and size"));
			// cập nhật lại số lượng hàng trong kho 
		  inventory.setQuantity(inventory.getQuantity() + orderDetail.getQuantity());
		  inventoryDao.save(inventory);
		});
		// xóa đơn hàng
		orderDao.deleteById(id);
	}
	
	// tong don hang 
	public Long countAllOrder() {
		return orderDao.count();
	}
}
