package tutorial.ecommerce_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tutorial.ecommerce_backend.api.DTO.DetailCartDto;
import tutorial.ecommerce_backend.dao.CartDao;
import tutorial.ecommerce_backend.dao.DetailCartDao;
import tutorial.ecommerce_backend.dao.InvetoryDao;
import tutorial.ecommerce_backend.dao.LocalUserDao;
import tutorial.ecommerce_backend.dao.ProductDao;
import tutorial.ecommerce_backend.dao.SizeDao;
import tutorial.ecommerce_backend.entity.Cart;
import tutorial.ecommerce_backend.entity.DetailCart;
import tutorial.ecommerce_backend.entity.Inventory;
import tutorial.ecommerce_backend.entity.LocalUser;
import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.entity.Size;
import tutorial.ecommerce_backend.exception.DetailCartNotFoundException;
import tutorial.ecommerce_backend.jwt.JWTService;
import tutorial.ecommerce_backend.pagination.PostPageRequest;

@Service
public class DetailCartService {

	@Autowired
	private DetailCartDao detailCartDao;
	@Autowired
	private JWTService jwtService;
	@Autowired
	private CartDao cartDao;
	@Autowired
	private LocalUserDao userDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SizeDao sizeDao;
	@Autowired
	private InvetoryDao invetoryDao;

	private Cart getCartByToken(String token) {
		LocalUser user = jwtService.getUsernameByToken(token);
			if(user == null) {
				return null;
			}

		// Lấy giỏ hàng của người dùng từ cơ sở dữ liệu
		Cart cart = cartDao.findByUser(user)
				.orElseThrow(() -> new RuntimeException("Cart not found for user: " ));

		return cart;
	}

	public List<DetailCartDto> getDetailCarts() {
		return detailCartDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private DetailCartDto convertToDto(DetailCart detailCart) {
		DetailCartDto dto = new DetailCartDto();
		dto.setId(detailCart.getId());
		dto.setName(detailCart.getProduct().getName());
		dto.setPathImage(detailCart.getProduct().getPathImage());
		dto.setPrice(detailCart.getProduct().getPrice());
		dto.setQuantity(detailCart.getQuantity());
		dto.setSize(detailCart.getSize().getSize());
		return dto;
	}

	public void addDetailCart(Product product, Integer quantity, String token, String size) {
	    // Lấy giỏ hàng của người dùng từ token
	    Cart cart = getCartByToken(token);

	    // Tìm sản phẩm từ cơ sở dữ liệu (hoặc có thể là đã tồn tại)
	    Product existingProduct = productDao.findByName(product.getName())
	            .orElseThrow(() -> new RuntimeException("Product not found for name: " + product.getName()));

	    // Tìm kích thước từ cơ sở dữ liệu
	    Size opSize = sizeDao.findBySize(size)
	            .orElseThrow(() -> new RuntimeException("Size not found for name: " + size));

	    // Kiểm tra số lượng tồn kho
	    Inventory inventory = invetoryDao.findByProductAndSize(existingProduct, opSize)
	            .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + existingProduct.getName()
	                    + " and size: " + opSize.getSize()));

	    // Kiểm tra số lượng hàng tồn kho còn lại sau khi thêm sản phẩm vào giỏ hàng
	    int remainingStock = inventory.getQuantity();
	    Optional<DetailCart> existingDetailCartOpt = detailCartDao.findByProductAndCartAndSize(existingProduct, cart, opSize);

	    if (existingDetailCartOpt.isPresent()) {
	        // Nếu chi tiết giỏ hàng đã tồn tại, chỉ cần cập nhật số lượng
	        DetailCart existingDetailCart = existingDetailCartOpt.get();
	        int newQuantity = quantity + existingDetailCart.getQuantity();

	        // Kiểm tra xem số lượng sau khi cập nhật có vượt quá số lượng tồn kho không
	        if (remainingStock >= newQuantity) {
	            existingDetailCart.setQuantity(newQuantity);
	            detailCartDao.save(existingDetailCart);
	        } else {
	            throw new RuntimeException("Not enough stock for product: " + existingProduct.getName() 
	                    + " and size: " + opSize.getSize());
	        }
	    } else {
	        // Nếu chi tiết giỏ hàng chưa tồn tại, tạo mới
	        if (remainingStock >= quantity) {
	            DetailCart detailCart = new DetailCart();
	            detailCart.setCart(cart);
	            detailCart.setProduct(existingProduct);
	            detailCart.setQuantity(quantity);
	            detailCart.setSize(opSize);
	            detailCartDao.save(detailCart);
	        } else {
	            throw new RuntimeException("Not enough stock for product: " + existingProduct.getName() 
	                    + " and size: " + opSize.getSize());
	        }
	    }
	}


	public void removeDetailCart(Long id) {
		Optional<DetailCart> opDetailCart = detailCartDao.findById(id);
		if (opDetailCart.isPresent()) {
			DetailCart detailCart = opDetailCart.get();
			detailCartDao.delete(detailCart);
		} else {
			throw new DetailCartNotFoundException("DetailCart not found for id: " + id);
		}
	}

	public int getTotalQuantityInCart(String token) {
		Cart cart = getCartByToken(token);
		return detailCartDao.getTotalQuantityInCart(cart.getId());
	}

	public Page<DetailCartDto> getDetailForUser(String token, int offset, Sort sort) {
		Cart cart = getCartByToken(token);
		Pageable pageable = new PostPageRequest(offset, sort != null ? sort : Sort.unsorted());
		Page<DetailCart> details = detailCartDao.findAllProductByCart(cart.getId(), pageable);

		if (details == null || !details.hasContent()) {
			return Page.empty();
		}

		// Convert DetailCart to DetailCartDto
		List<DetailCartDto> dtoList = details.getContent().stream().map(this::convertToDto)
				.collect(Collectors.toList());

		// Return Page<DetailCartDto>
		return new PageImpl<>(dtoList, pageable, details.getTotalElements());
	}

	@Transactional
	public void updateQuantity(String productName, int quantity, String token, String size) {
		Cart cart = getCartByToken(token);
		// Tìm sản phẩm từ cơ sở dữ liệu (hoặc có thể là đã tồn tại)
		Product existingProduct = productDao.findByName(productName)
				.orElseThrow(() -> new RuntimeException("Product not found for id: " + productName));

		Size opSize = sizeDao.findBySize(size)
				.orElseThrow(() -> new RuntimeException("Size not found for name: " + size));

		DetailCart detailCart = detailCartDao.findByProductAndCartAndSize(existingProduct, cart, opSize)
				.orElseThrow(() -> new RuntimeException("Can not update quantity "));
		detailCart.setQuantity(quantity);

		// so luong bang 0 thi xoa product do ra khoi detailcart
		if (quantity == 0) {
			detailCartDao.delete(detailCart);
			return;
		}

		detailCartDao.save(detailCart);

	}

}
