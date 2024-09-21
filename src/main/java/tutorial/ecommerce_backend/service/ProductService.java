package tutorial.ecommerce_backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import tutorial.ecommerce_backend.admin.dto.AdminProductDto;
import tutorial.ecommerce_backend.api.DTO.ProductDTO;
import tutorial.ecommerce_backend.dao.CategoryDao;
import tutorial.ecommerce_backend.dao.InvetoryDao;
import tutorial.ecommerce_backend.dao.ProductDao;
import tutorial.ecommerce_backend.dao.SizeDao;
import tutorial.ecommerce_backend.dao.SubCategoryDao;
import tutorial.ecommerce_backend.entity.Inventory;
import tutorial.ecommerce_backend.entity.Product;
import tutorial.ecommerce_backend.entity.Size;
import tutorial.ecommerce_backend.entity.SubCategory;
import tutorial.ecommerce_backend.pagination.PostPageRequest;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private SizeDao sizeDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private InvetoryDao inventoryDao;
	@Autowired
	private SubCategoryDao subCategoryDao;

	public List<Product> getProducts() {
		return productDao.findAll();
	}

	public ProductDTO getProductById(Long id) {
		Optional<Product> productOptional = productDao.findById(id);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(product.getName());
			productDTO.setPrice(product.getPrice());
			productDTO.setPathImage(product.getPathImage());
			productDTO.setSubCategoryName(product.getSubCategory().getName());

			// Create a map to store size and quantity
			Map<String, Integer> sizeQuantities = new HashMap<>();
			for (Inventory inventory : product.getInventories()) {
				Size size = inventory.getSize();
				sizeQuantities.put(size.getSize(), inventory.getQuantity());
			}
			productDTO.setSizeQuantities(sizeQuantities);

			return productDTO;
		} else {
			return null;
		}
	}

	public Page<Product> getProductBySubCategory(Long subCategoryId, int offset, Sort sort) {
		Optional<SubCategory> opSupcategory = subCategoryDao.findById(subCategoryId);
		if (!opSupcategory.isPresent()) {
			throw new RuntimeException("subCategory not found");
		}
		SubCategory subCategory = opSupcategory.get();
		Pageable pageable = new PostPageRequest(offset, sort);
		Page<Product> products = productDao.findBySubCategory(subCategory, pageable);
		return products;
	}

	@Transactional
	public void addProduct(AdminProductDto productDTO) {
		try {
			// Tìm sản phẩm theo tên
			Optional<Product> opProduct = productDao.findByName(productDTO.getName());

			Product product;
			if (opProduct.isPresent()) {
				// Sản phẩm đã tồn tại, lấy sản phẩm từ cơ sở dữ liệu
				product = opProduct.get();
			} else {
				// Sản phẩm chưa tồn tại, tạo mới
				product = new Product();
				product.setName(productDTO.getName());
				product.setPrice(productDTO.getPrice());
				product.setPathImage(productDTO.getPathImage());

				// Tìm kiếm và thiết lập danh mục
				SubCategory subcategory = subCategoryDao.findByName(productDTO.getSubCategoryName())
						.orElseThrow(() -> new RuntimeException("SubCategory not found"));
				product.setSubCategory(subcategory);
				productDao.save(product);
			}

			Size size = sizeDao.findBySize(productDTO.getSize())
					.orElseThrow(() -> new RuntimeException("Size not found"));

			// Cập nhật hoặc tạo mới Inventory
			Optional<Inventory> opInventory = inventoryDao.findByProductAndSize(product, size);
			Inventory inventory;

			if (opInventory.isPresent()) {
				// Cập nhật Inventory nếu đã tồn tại
				inventory = opInventory.get();
				inventory.setQuantity(productDTO.getQuantity());
			} else {
				// Tạo mới Inventory nếu chưa tồn tại
				inventory = new Inventory();
				inventory.setProduct(product);
				inventory.setQuantity(productDTO.getQuantity());
				inventory.setSize(size);
			}

			inventoryDao.save(inventory);
		} catch (Exception e) {
			// Xử lý lỗi, ghi log hoặc ném ngoại lệ phù hợp
			System.err.println("Error adding product: " + e.getMessage());
			throw new RuntimeException("Failed to add product", e);
		}
	}

	public void removeProduct(Long id) {
		Optional<Product> product = productDao.findById(id);
		if (product.isPresent()) {
			productDao.delete(product.get());
		}
		return;
	}

	@Transactional
	public void modifyProduct(AdminProductDto productDTO) {
		try {
			// Tìm sản phẩm theo tên
			Optional<Product> opProduct = productDao.findById(productDTO.getId());
			if (opProduct.isPresent()) {
				Product product = opProduct.get();

				// Cập nhật thông tin sản phẩm
				product.setName(productDTO.getName());
				product.setPrice(productDTO.getPrice());
				product.setPathImage(productDTO.getPathImage());

				// Tìm kiếm và thiết lập danh mục
				SubCategory subcategory = subCategoryDao.findByName(productDTO.getSubCategoryName())
						.orElseThrow(() -> new RuntimeException("SubCategory not found"));
				product.setSubCategory(subcategory);
				productDao.save(product);

//				// Cập nhật hoặc tạo mới Inventory
//				Size size = sizeDao.findBySize(productDTO.getSize())
//						.orElseThrow(() -> new RuntimeException("Size not found"));
//				Optional<Inventory> opInventory = inventoryDao.findByProductAndSize(product, size);
//				Inventory inventory;
//
//				if (opInventory.isPresent()) {
//					// Cập nhật Inventory hiện tại
//					inventory = opInventory.get();
//					inventory.setQuantity(productDTO.getQuantity());
//				} else {
//					// Tạo mới Inventory nếu chưa tồn tại
//					inventory = new Inventory();
//					inventory.setProduct(product);
//					inventory.setSize(sizeDao.findBySize(productDTO.getSize())
//							.orElseThrow(() -> new RuntimeException("Size not found")));
//					inventory.setQuantity(productDTO.getQuantity());
//				}
//
//				inventoryDao.save(inventory);
			} else {
				throw new RuntimeException("Product not found");
			}
		} catch (Exception e) {
			// Xử lý lỗi
			System.err.println("Error modifying product: " + e.getMessage());
			throw new RuntimeException("Failed to modify product", e);
		}
	}

	public Page<Product> searchProduct(String name, int offset, Sort sort) {
		Pageable pageable = new PostPageRequest(offset, sort);
		Specification<Product> spec = hasKeywords(name);
		return productDao.findAll(spec, pageable);

	}

	private Specification<Product> hasKeywords(String keywords) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			if (keywords == null || keywords.trim().isEmpty()) {
				return cb.conjunction();
			}

			String[] keywordArray = keywords.trim().split("\\s+");
			Predicate predicate = cb.conjunction();
			for (String keyword : keywordArray) {
				predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
			}
			return predicate;
		};
	}

	public Page<Product> getProductsByGender(String gender, int offset, Sort sort) {
		Pageable pageable = new PostPageRequest(offset, sort);
		Page<Product> products = productDao.findByGender(gender, pageable);
		if (products != null) {
			return products;
		}
		return null;
	}
	
	public List<Product> getRandomProducts(int quantity){
		return productDao.getRandomProduct(quantity);
	}
}
